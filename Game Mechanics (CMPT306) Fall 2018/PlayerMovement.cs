using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : CharacterStats {
    Rigidbody2D bod;
    Animator anime;
    SpriteRenderer rend;

    private bool isRight;

    // Holds the players velocity
    float velocity = 5.0f;

	// Use this for initialization
	void Awake () {
        isRight = true;
        // Get the Player Mechanics
        bod = GetComponent<Rigidbody2D>();
        anime = GetComponent<Animator>();
        rend = GetComponent<SpriteRenderer>();
       
        // Freeze the rotation
        bod.freezeRotation = true;  // THIS IS VERY IMPORTANT!
    }
	
	// Update is called once per frame
	void Update () {
        
        // If the mouse bitton is clicked, attack
        if (Input.GetKeyDown(KeyCode.F))
        {
            Respect();
        }
        // Gets input for the player to move
        BasicMovement();
        
    }

    // Get the Movement Input
    void BasicMovement()
    {
        // Flip Player when Moving Left
        /*
        float xvel = bod.velocity.x;
        float yvel = bod.velocity.y;

        // Flips the character if he is moving left
        if (xvel < 0)
        {
            rend.flipX = true;
        }
        else
        {
            rend.flipX = false;
        }
        */
        // Get the input from the player
        float horizontal = Input.GetAxis("Horizontal");
        Vector2 inputVector = new Vector2(horizontal, Input.GetAxis("Vertical")); // Get the input for the player
        /* lloyd's code leaving in for clarity reasons
        // Determine movement animations
        if (inputVector.x != 0) // move side to side if x is non-zero
        {
            anime.SetFloat("Speed", Mathf.Abs(inputVector.x));
        }
        else if(inputVector.y != 0.0f && inputVector.x == 0.0f)   
        {   
            // Determine if you are moving up or down
            if (inputVector.y > 0.0f)
            {
                anime.SetFloat("Up", Mathf.Abs(inputVector.y));     // Move up 
            }
            else
            { 
                anime.SetFloat("Down", Mathf.Abs(inputVector.y));   // Move Down
            }
        }*/
        if(inputVector.x != 0) // checks to see if there is any movement on x, this gives priority to horizontal movement
        {
            anime.SetFloat("Up", 0.0f);
            anime.SetFloat("Down", 0.0f);
            anime.SetFloat("Speed", Mathf.Abs(inputVector.x));
        }
        else if (inputVector.y > 0) // if the y value is positive move up
        {
            anime.SetFloat("Down", 0.0f);
            anime.SetFloat("Up", Mathf.Abs(inputVector.y));
        }
        else if (inputVector.y < 0) // if the y value is negative move down
        { 
            anime.SetFloat("Up", 0.0f);
            anime.SetFloat("Down", Mathf.Abs(inputVector.y));
        }
        else // set to idle animation
        {
            anime.SetFloat("Speed", 0.0f);
            anime.SetFloat("Up", 0.0f);
            anime.SetFloat("Down", 0.0f);
        }


        bod.velocity = (inputVector * velocity);    // Applies the velocity to the character's movement
        //bod.AddForce(inputVector * 7.0f);   // applies the force to the character

        Flip(horizontal);
    }

    // This will control the player's attack animation and damage
    void Attack()
    {
        anime.SetTrigger("Attack"); // Triggers the attack animation
        
    }

    void IncreaseSpeed(float speedIncr)
    {
        velocity += speedIncr;
    }

    void Respect()
    {
        Debug.Log("Press 'F' to pay respect.");
        anime.SetTrigger("Emote");
    }

    private void Flip (float horizontal)
    {
        if(horizontal > 0 && !isRight || horizontal < 0 && isRight)
        {
            isRight = !isRight;

            Vector3 theScale = transform.localScale;

            theScale.x *= -1;

            transform.localScale = theScale;
        }
    }

    void DamageCharacter(int damage)
    {
        anime.SetTrigger("Damaged");
        cur_Health -= damage;
      
        checkIfDead();
    }
}
