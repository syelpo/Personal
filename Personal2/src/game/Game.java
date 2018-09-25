package game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

//Finish meteor spawning, movement towards earth
//hi there i am making changes

public class Game extends PApplet
{
	int increment = 0;
	int red = 50, green = 50, blue = 50;
	int percentage = increment;
	int level = 1;
	float characterX = 550, characterY = 450, speedX = 0, speedY = 0;
	float enemyX = 750, enemyY = 750, enemySpeedX = 7, enemySpeedY = 5;
	float distance;
	float easing = 0.2F;
	int earthX = 800, earthY = 450;
	int score, lives = 4;
	int timeOfNextChange;
	int hitCounter = 0;
	int hitpoints = 1000;
	int timeToComplete = 0;
	int diamondCounter = 0;
	int scoreDuringLife4, scoreDuringLife3, scoreDuringLife2, scoreDuringLife1;
	
	PImage johnnyTest;
	PImage Stage1Background;
	PImage MordecaiHead;
	PImage enemy;
	PImage Stage2Background;
	PImage JimmyNeutron;
	PImage SpeechBubble;
	PImage Stage3Background;
	PImage Stage3Cleared;
	PImage Stage4Background;
	
	PFont emulogic;
	
	public static void main(String[] args) 
	{
		PApplet.main("horseTornado.HorseTornado");
		
	}
	public void settings()
	{
		size(1600, 900);
	}
	public void setup()
	{
		MordecaiHead = loadImage("MordecaiHead.png");
		Stage1Background = loadImage("Stage1Background.png");
		johnnyTest = loadImage("johnnyTest.png");
		increment = 0;
		enemy = loadImage("enemy.png");
		Stage2Background = loadImage("Stage2Background.jpg");
		emulogic = createFont("Emulogic Regular", 32);
		JimmyNeutron = loadImage("JimmyNeutron.png");
		SpeechBubble = loadImage("SpeechBubble.png");
		Stage3Background = loadImage("Stage3Background.png");
		Stage3Cleared = loadImage("Stage3Cleared.png");
		Stage4Background = loadImage("Stage4Background.png");
	}
	public void draw()
	{
		background(red, green, blue);
		
		if(increment <= 1000)
			loadingScreen();
		
		else if(level == 1)
		{
			drawStage1();
		}	
		
		else if(level == 2)
		{
			stage1Cleared();
		}
		else if(level == 3)
		{
			drawStage2();
		}
		else if(level == 4)
		{
			stage2Cleared();
		}
		else if(level == 5)
		{
			drawStage3();
		}
		else if(level == 6)
		{
			stage3Cleared();
		}
		else if(level == 7)
		{
			drawStage4();
		}
	}
	public void loadingScreen()
	{
		if(increment <= 1000)
		{
			level = 0;
			fill(0);
			rect(300, 700, increment, 100);
			noFill();
			rect(300, 700, 1000, 100);
					
			textAlign(CENTER, CENTER);
			textSize(32);
			text(percentage + "%", 800, 650);
			
			textSize(100);
			text("Loading...", 800, 200);
			
			textSize(20);
			
			text("Welcome", 800, 350);
			
			
		}
		level = 1;
		increment += 10;
		percentage += 1;
		
	}
	public void drawCharacter()
	{

		imageMode(CENTER);
		image(johnnyTest, characterX, characterY, 174, 252);
	
		
	}
	public void mousePressed() //Left-click to activate fail-safe
	{
		jumpToRandomLocation();
	}
	public void jumpToRandomLocation() //Fail-safe if either character gets stuck
	{
		characterX = random(75, 1475);
		characterY = random(75, 825);
		enemyX = random(75, 1475);
		enemyY = random(75, 825);
	}
	public void keyPressed() //Arrow keys change the direction of the character
	{
		if(keyCode == UP)
			speedY = -5;
		
		if(keyCode == DOWN)
			speedY = 5;
			
		if(keyCode == LEFT)
			speedX = -5;
		
		if(keyCode == RIGHT)
			speedX = 5;
		
		if(keyCode == ENTER) // Pause
			noLoop();
		
		if(keyCode == SHIFT) // Resume
			loop();
	}
	public void drawEnemy()
	{
		fill(0, 255, 0);
		ellipse(enemyX, enemyY, 150, 150);
	}
	public void scoreCounter()
	{
		if(lives >= 1)
			score = millis() / 1000;
		
		else if(lives <= 0)
			score = score;
		
		fill(255, 0, 0);
		textSize(26);
		text("Score: " + score, 1450, 50);
	}
	public void increaseSpeed() //Increases speed of character and enemy with time
	{
		if(millis() >= timeOfNextChange)
		{
			timeOfNextChange = millis() + 10000;
			
			if(enemySpeedX < 0)
				enemySpeedX -= random(5);
			else
				enemySpeedX += random(5);
			
			if(enemySpeedY < 0)
				enemySpeedY -= random(5);
			else
				enemySpeedY += random(5);
			
			if(score == 50)
			{
				enemySpeedX *= -1;
				enemySpeedY *= -1;
			}
		}
	}
	public void ifCollided() //Also contains score during current life
	{
		fill(255, 0, 0);
		distance = dist(enemyX, enemyY, characterX, characterY);
		if(distance <= 90)
		{
				lives -= 1;
				jumpToRandomLocation();
				background(255, 0, 0);
		}
		if(lives > 0)
		{
			textSize(26);
			text("Lives left: " + lives, 1450, 100);
			
			if(lives == 4)
			{
				scoreDuringLife4 = millis() / 1000;
				fill(255, 0, 0);
				textSize(26);
				text("Score during current life: " + scoreDuringLife4, 1400, 150);
			}
			if(lives == 3)
			{
				scoreDuringLife4 = scoreDuringLife4;
				scoreDuringLife3 = (millis() / 1000) - scoreDuringLife4;
				fill(255, 0, 0);
				textSize(26);
				text("Score during current life: " + scoreDuringLife3, 1400, 150);
			}
			if(lives == 2)
			{
				scoreDuringLife3 = scoreDuringLife3;
				scoreDuringLife2 = (millis() / 1000) - (scoreDuringLife4 + scoreDuringLife3);
				fill(255, 0, 0);
				textSize(26);
				text("Score during current life: " + scoreDuringLife2, 1400, 150);
			}
			if(lives == 1)
			{
				scoreDuringLife2 = scoreDuringLife2;
				scoreDuringLife1 = (millis() / 1000) - (scoreDuringLife4 + scoreDuringLife3 + scoreDuringLife2);
				fill(255, 0, 0);
				textSize(26);
				text("Score during current life: " + scoreDuringLife1, 1400, 150);
			}
		}
		if(lives <= 0)
		{
			background(255, 0, 0);
			
			fill(0);
			textSize(75);
			text("GAME OVER!", 275, 150);
			
			fill(0);
			textSize(26);
			text("Final Score", 430, 450);
			
			fill(0);
			textSize(50);
			text(score, 477, 500);
		}
	}
	public void drawRectangles() //Each rectangle represents a life
	{
		if(lives == 4)
		{
			fill(0, 255, 0);
			rect(50, 50, 75, 75);
			fill(0, 255, 0);
			rect(150, 50, 75, 75);
			fill(0, 255, 0);
			rect(250, 50, 75, 75);
			fill(0, 255, 0);
			rect(350, 50, 75, 75);
		}
		else if(lives == 3)
		{
			fill(255, 255, 0);
			rect(50, 50, 75, 75);
			fill(255, 255, 0);
			rect(150, 50, 75, 75);
			fill(255, 255, 0);
			rect(250, 50, 75, 75);
		}
		else if(lives == 2)
		{
			fill(255, 144, 0);
			rect(50, 50, 75, 75);
			fill(255, 144, 0);
			rect(150, 50, 75, 75);
		}
		else if(lives >= 1)
		{
			fill(255, 0, 0);
			rect(50, 50, 75, 75);
		}
	}
	public void characterMovement() //Controls bouncing of character and enemy
	{
		if(characterY <= 25 || characterY >= 875)
			speedY = speedY * -1;
		
		if(characterX <= 25 || characterX >= 1575)
			speedX = speedX * -1;
		
		characterY = characterY + speedY;
		characterX = characterX + speedX;
		
		if(enemyY <= 75 || enemyY >= 825)
			enemySpeedY = enemySpeedY * -1;
		
		if(enemyX <= 75 || enemyX >= 1525)
			enemySpeedX = enemySpeedX * -1;
		
		enemyY = enemyY + enemySpeedY;
		enemyX = enemyX + enemySpeedX;
	}
	public void drawStage1()
	{
		if(score < 30)
		{
			imageMode(CORNER);
			image(Stage1Background, 0, 0, width, height);
			drawCharacter();
			drawEnemy();
			scoreCounter();
			increaseSpeed();
			ifCollided();
			drawRectangles();
			characterMovement();
		}
		if(score >= 30)
		{
			level = 2;
		}	
	}
	public void stage1Cleared()
	{
		textAlign(CENTER);
		textSize(50);
		text("Press any key to move on", 800, 450);
		
		if(keyPressed)
		{
			level = 3;
		}
			
	}
	public void stage2Characters()
	{
		image(Stage2Background, 800, 450, width, height);
		image(MordecaiHead, mouseX, mouseY, 84, 66);
		image(enemy, enemyX, enemyY);
		
	}
	public void drawStage2()
	{
		stage2Characters();
		enemyBouncingStage2();
		enemyCollision();
		
	}
	public void enemyBouncingStage2()
	{
		
		if(enemyY <= 75 || enemyY >= 825)
			enemySpeedY = enemySpeedY * -1;
		
		if(enemyX <= 75 || enemyX >= 1525)
			enemySpeedX = enemySpeedX * -1;
		
		enemyY = enemyY + enemySpeedY;
		enemyX = enemyX + enemySpeedX;
		
	}
	public void enemyCollision()
	{
		int time = (millis()/1000) - 30;
		distance = dist(enemyX, enemyY, mouseX, mouseY);
		if(distance <= 140)
		{
				jumpToRandomLocation();
				background(255, 0, 0);
				hitCounter++;
		}
		textSize(32);
		
		textFont(emulogic);
		text("Collisions: " + hitCounter, 1400, 100);
		text("Time: " + time, 1400, 150);
		if(hitCounter ==   151)
			time = time;
			timeToComplete = time;
		
		if(timeToComplete < 60 && hitCounter > 150)
			level = 4;
		if(time > 60)
		{
			background(255, 0, 0);
			
			fill(0);
			textSize(75);
			text("GAME OVER!", 275, 150);
		}
	}
	public void stage2Cleared()
	{
		image(JimmyNeutron, 800, 450);
		image(SpeechBubble, 1000, 350, 400, 400);
		textSize(18);
		text("Press any key to move on", 1000, 350);
		if(keyPressed)
			level = 5;
	}
	public void drawStage3()
	{
		int stage3Timer = (millis() / 1000) - (timeToComplete + 30);
		image(Stage3Background, 800, 450, width, height);
		text("Mine as many diamonds as possible in 60 seconds.", 300, 300);
		text("Diamonds Mined: " + diamondCounter, 1400, 100);
		text("Time: " + stage3Timer, 1400, 150);
		if(diamondCounter >= 20 && stage3Timer <= 60)
			level = 6;
	}
	public void mouseClicked()
	{
		if(level == 5 && ((mouseX > 700 && mouseY > 570) && (mouseX < 920 && mouseY < 670)))
			diamondCounter++;
	}
	public void stage3Cleared() 
	{
		
		text("Press any key to move on", 800, 800);
		if(keyPressed)
			level = 7;
	}
	public void drawStage4()
	{
		image(Stage4Background, earthX, earthY, width, height);
	}
	public void hpBar()
	{
		fill(255, 0, 0);
		rectMode(CORNER);
		rect(50, 75, 75, 500);
		noFill();
		text("Hitpoints: " + hitpoints, 50, 25);
	}
	public void spawnMeteors(int meteorX, int meteorY, int diameter)
	{
		fill(43, 26, 3);
		ellipse(meteorX, meteorY, diameter, diameter);
	}

}

