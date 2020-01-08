using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerAttack : MonoBehaviour {

    // Damage the unit will deal
    public float dmg;
    // range of the unit
    public float range;

    // The enemies we want to hit
    Collider2D[] EnemiesToHit;

    // Which idle attack position to use
    private bool isRight;

    private float attackTimer = 0;
    private float attackCoolDown = 0.3f;


    private Transform atkLocal;
    // Where the player will be attacking
    public Transform atkPos;
    public Transform atkPosLeft;
    public Transform atkPosUp;
    public Transform atkPosDown;

    Vector2 change;

    private enum AtkDirection { atkUp, atkRight, atkDown, atkLeft };
    private AtkDirection atkDir;

    // Layermask
    public LayerMask whatIsEnemies;

    private Animator anime;

    // Use this for initialization
    void Awake()
    {
        isRight = true;
        anime = GetComponent<Animator>();
        atkLocal = atkPos;

        atkDir = AtkDirection.atkRight;
        change = getKillZone();
    }


    private void Update()
    {
        GetAtkDir();
        change = getKillZone();

        if ((Input.GetKeyDown(KeyCode.Space) || Input.GetMouseButtonDown(0)) && attackTimer <= 0)
        {
            Atk();
        }
        else
        {  // if the attack is still on cool down, deduce the time reamaining
            attackTimer -= Time.deltaTime;
        }
        
    }

    private void GetAtkDir()
    {
        if (Input.GetAxis("Horizontal") == 1)
        {
            atkDir = AtkDirection.atkRight;
            //Debug.Log("Moving right");
        }
        else if (Input.GetAxis("Horizontal") == -1)
        {
            atkDir = AtkDirection.atkLeft;
            //Debug.Log("Moving left");
        }
        else if (Input.GetAxis("Vertical") == 1)
        {
            atkDir = AtkDirection.atkUp;
            //Debug.Log("Moving up");
        }
        else if (Input.GetAxis("Vertical") == -1)
        {
            atkDir = AtkDirection.atkDown;
            //Debug.Log("Moving down");
        }
    }

    void Atk()
    {
        //Debug.Log("Attacking");
        // The cool down between attacks
        attackTimer = attackCoolDown;
        
        if (atkDir == AtkDirection.atkRight)
        {
            atkLocal = atkPos;
        }
        else if (atkDir == AtkDirection.atkLeft)
        {
            if (isRight)
            {
                isRight = !isRight;
            }
            atkLocal = atkPosLeft;
        }
        else if (atkDir == AtkDirection.atkUp)
        {
            atkLocal = atkPosUp;
        }
        else if (atkDir == AtkDirection.atkDown)
        {
            atkLocal = atkPosDown;
        }
       
        // This tells what the program is going to hit
        EnemiesToHit = Physics2D.OverlapCircleAll(atkLocal.position, range, whatIsEnemies);
        Debug.Log("Found " + EnemiesToHit.Length + " enemies!");
        if (EnemiesToHit != null || EnemiesToHit.Length != 0)
        {
            
            if (atkLocal == atkPos)
            {
                Debug.Log("Attaking Right!");
            }
            else if (atkLocal == atkPosLeft)
            {
                Debug.Log("Attaking Left!");
            }
            else if (atkLocal == atkPosUp)
            {
                Debug.Log("Attaking Up!");
            }
            else if (atkLocal == atkPosDown)
            {
                Debug.Log("Attaking Down");
            }

            // Triggers the attack animation
            if (atkLocal == atkPosUp)
            {
                anime.SetTrigger("Attack Up");
            }
            else if (atkLocal == atkPosDown)
            {
                anime.SetTrigger("Attack Down");
            }
            else
            {
                anime.SetTrigger("Attack");
            }

            foreach(Collider2D enemy in EnemiesToHit)
            {
                enemy.SendMessage("TakeDamage", dmg);
            }
            /*
            for (int i = 0; i < EnemiesToHit.Length; i++)
            {
                // Tell all the enemies they are taking and damage them
                
                    if (EnemiesToHit.Length > 0)
                    {
                        GameObject enemy = EnemiesToHit[i].gameObject;
                        float distance = Vector2.Distance(enemy.transform.position, atkLocal.transform.position);
                        if (distance <= range) {
                            EnemiesToHit[i].SendMessage("TakeDamage", dmg);
                            //EnemiesToHit[i].GetComponent<EnemyStats>().TakeDamage(dmg);
                        }
                    }
            } */
                
            
        }
    }

    Vector2 getKillZone()
    {
        Vector2 inputVector = new Vector2(Input.GetAxis("Horizontal"), Input.GetAxis("Vertical"));

        return inputVector;
    }

    // Increase the damage of the attack
    void IncreaseDmg(int damgUp)
    {
        dmg += damgUp;
    }

    // Increase the range of the attack
    void IncreaseRange(int rangeUp)
    {
        range += rangeUp;
    }

    // This creates the wire representation in the game scene
    void OnDrawGizmosSelected()
    {
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(atkPos.position, range);
        Gizmos.DrawWireSphere(atkPosLeft.position, range);
        Gizmos.DrawWireSphere(atkPosUp.position, range);
        Gizmos.DrawWireSphere(atkPosDown.position, range);
    }
}
