using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyStats : MonoBehaviour
{
    // Health stats
    public float maxHealth;
    public float currentHealth;

    //Movement speed
    public float mvSpeed;

    //Attack stats
    public float attackDamage;
    public float attackingRange;
    //public float detectRange;

    //Modifies enemy stats
    public Color enemyColor;
    public bool isPorygon = false;
    public bool hasAltColor;

    //Misc private things
    //private float timer = 0f;
    private Animator anim;
    private float damage;
    
    private void Start()
    {
        anim = GetComponent<Animator>();
    }

    // Update is called once per frame
    void Update()
    {

        if (currentHealth <= 0)
        {
            Destroy(gameObject);
        }
    }

    public void TakeDamage(float dmg)
    {
        //Debug.Log("animation call");
        this.damage = dmg;
        Debug.Log("Do take damage animation");
        anim.SetTrigger("EnemyS1DamageRight");
        
        doTakeDamage(dmg);
    }

    public void doTakeDamage(float dmg)
    {
                
        //Debug.Log("Health decrement");
        currentHealth -= this.damage;
        //Debug.Log(this.damage + " Damage Taken! " + "\n current health: " + currentHealth);
    }

    void OnDrawGizmosSelected()
    {
        Gizmos.color = Color.green;
        Gizmos.DrawWireSphere(this.transform.position, attackingRange);
    }

}
