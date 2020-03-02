using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CharacterStats : MonoBehaviour {

    // Base stats for the player
    public float maxHealth;
    public float cur_Health;

    public Animator anim;

    private void Start()
    {
        anim = GetComponent<Animator>();

        if (GameManager.instance != null)
        {
            maxHealth = GameManager.instance.playerMaxHP;
            cur_Health = GameManager.instance.playerHP;
        }
    }

    // Update is called once per frame
    void Update () {
        checkIfDead();
	}


    public void checkIfDead()
    {
        if(cur_Health <= 0)
        {
            // put code here for death animation
            Debug.Log("Player is dead");
            //GameManager.instance.Reset();
        }
    }

    void gainHealth(int health)
    {
        cur_Health += health;
        if(cur_Health > maxHealth)
        {
            cur_Health = maxHealth;
        }
    }

    void increaseMaxHealth(int maxIncrease)
    {
        maxHealth += maxIncrease;
    }
    
}
