/*******************************************************************************
 * Robot Animations -- Functions to execute the bling "actions"
 ******************************************************************************/

/*
 * Solid colors - remember to add 'delay()' to listen for new commands
 */
void solid(rgbColor c)
{
  setAllPixels(c.color);
  showPixels();
  while (1) { delay(10); }
}

/**
 * Set strip to full brightness and display the solid color
 */
void solidWithFullBrightness(rgbColor c)
{
  setFullBrightness();
  solid(c);
}

/**
 * Set strip to full brightness and display the solid color
 */
void solidWithBrightness(rgbColor c, uint8_t brightness)
{
  pixels.setBrightness(brightness);
  solid(c);
}

/**
 * Display solid color with 'off' in between to give illusion of 'blink'
 */
void solidWithBlink(rgbColor c, uint8_t wait)
{
  solid(c);
  blinkOffWithDelay(wait);
}

/*
 * Turns off the pixels creating a 'blink' animation
 */
void blinkOffWithDelay(uint8_t wait)
{
  // Wait prior to turning off pixels
  delay(wait);
  setAllPixelsOff();
  delay(wait);
}

 void strobe(rgbColor c, uint8_t numFlashes, unsigned long flashDelay, unsigned long strobePause)
 {
 
  for (int j = 0; j < numFlashes; j++) 
  {
    setAllPixels(c);
    showPixels();
    
    delay(flashDelay);
    
    setAllPixels(rgbColor_OFF);
    showPixels();
    delay(flashDelay);
  }
 
 delay(strobePause);

}

void fillPixelByPixel(rgbColor c, uint8_t wait)
 {
  // start w/ pixel 0, set color, show color, wait and do it again
  for(uint16_t i=0; i<pixels.numPixels(); i++) 
  {
    // pass the color component of rgbColor 
    setPixel(i, c.color);
    showPixels();

    delay(wait);
  }
}


/** From the middle of the top, start illuminating pixels towards the ends
 * After length pixels have been added to each side, turn old pixels off
 * Pixels continue to scroll off of ends, then the scheme reverses
 */ 
void crawler(uint16_t timeInterval, uint32_t color, uint8_t length)
{
 
  uint8_t tempLength;
  uint8_t headUp;
  uint8_t headDown;
  uint8_t tailUp;
  uint8_t tailDown;

  while (1)
  {
    // Turn off the strip
    setAllPixelsOff();

    tempLength = length;
    headUp = (NUM_LEDS/2);
    headDown = headUp - 1;
    for (uint8_t i=0; i<length; i++)
    {
      setPixel(headUp++, color);
      setPixel(headDown--, color);
      showPixels();
      delay(timeInterval);
    }
    
    tailUp = (NUM_LEDS/2);
    tailDown = tailUp - 1;
    while (headUp < NUM_LEDS)
    {
      setPixel(headUp++, color);
      setPixel(headDown--, color);
      setPixel(tailUp++, OFF);
      setPixel(tailDown--, OFF);
      showPixels();
      delay(timeInterval);
    }
    
    while (tailUp < NUM_LEDS)
    {
      setPixel(tailUp++, OFF);
      setPixel(tailDown--, OFF);
      showPixels();
      delay(timeInterval);
    }

    // Reverse!
    tempLength = length;
    headUp = NUM_LEDS - 1;
    headDown = 0;
    for (uint8_t i=0; i<length; i++)
    {
      setPixel(headUp--, color);
      setPixel(headDown++, color);
      showPixels();
      delay(timeInterval);
    }
    
    tailUp = NUM_LEDS - 1;
    tailDown = 0;
    while (headUp >= (NUM_LEDS/2))
    {
      setPixel(headUp--, color);
      setPixel(headDown++, color);
      setPixel(tailUp--, OFF);
      setPixel(tailDown++, OFF);
      showPixels();
      delay(timeInterval);
    }
    
    while (tailUp >= (NUM_LEDS/2))
    {
      setPixel(tailUp--, OFF);
      setPixel(tailDown++, OFF);
      showPixels();
      delay(timeInterval);
    }
  }
}

// Flash of light for a short period of time, then off (runs once only)
void flash(unsigned long timeInterval, uint32_t color, uint8_t brightness)
{
  pixels.setBrightness(brightness);
  setAllPixels(color);
  showPixels();
  delay(timeInterval);
  setAllPixelsOff();
  showPixels();
}

// COGS start ------------------
/*
 * Spartronics blue and yellow "Cogs"
 */

// Number of LEDs per "cog" on the animation
#define COG_SIZE 5

// A counter from 0 to (2*COG_SIZE)-1
uint8_t cogOffset=0;

/* Spartronics cogs initialization */
void cogs_init(rgbColor color1, rgbColor color2)
{
  uint8_t led=0;

  while (led < NUM_LEDS)
  {
    for (uint8_t i=0; (i<COG_SIZE) && (led<NUM_LEDS); i++)
    {
      setPixel(led++, color1.color);
    }
    for (uint8_t i=0; (i<COG_SIZE) && (led<NUM_LEDS); i++)
    {
      setPixel(led++, color2.color);
    }
  }
  showPixels();
}

/* Set one pixel of each "cycle" to the specified color, starting at offset */
void setFromOffset(uint8_t offset, rgbColor color)
{
  for (uint8_t led=offset; (led<NUM_LEDS); led+=(2*COG_SIZE))
  {
    setPixel(led, color.color);
  }
}

/* Move the cogs up one pixel */
void moveCogsUp(rgbColor color1, rgbColor color2)
{
  cogOffset = (cogOffset + 1) % (2 * COG_SIZE);
  // Since we are moving cogs up, we increase our offset by one COG_SIZE
  // so that we add to the tail of the cog, rather than overwriting the
  // beginning
  uint8_t tempCogOffset = (cogOffset + COG_SIZE - 1) % (2 * COG_SIZE);
  if (tempCogOffset < COG_SIZE)
  {
    setFromOffset(tempCogOffset, color1);
    setFromOffset(tempCogOffset + COG_SIZE, color2);
  }
  else
  {
    setFromOffset(tempCogOffset, color1);
    setFromOffset(tempCogOffset - COG_SIZE, color2);
  }
}

/* Move the cogs down one pixel */
void moveCogsDown(rgbColor color1, rgbColor color2)
{
  // Shift the cogOffset down by one, but if it's zero then set it to
  // the last pixel in the first cog "cycle"
  if (cogOffset-- == 0)
  {
    cogOffset = (2 * COG_SIZE) - 1;
  }
  if (cogOffset < COG_SIZE)
  {
    setFromOffset(cogOffset, color1);
    setFromOffset(cogOffset + COG_SIZE, color2);
  }
  else
  {
    setFromOffset(cogOffset, color1);
    setFromOffset(cogOffset - COG_SIZE, color2);
  }
}

/* Show a bunch of cycles of upward rotation of the cogs */
void rotatingCogsUp(rgbColor colorOne, rgbColor colorTwo)
{
  for (int i = 0; i < (8 * COG_SIZE); i++) {
    moveCogsUp(colorOne, colorTwo);
    showPixels();
    _delay(100);
  }
}

/* Show a bunch of cycles of downward rotation of the cogs */
void rotatingCogsDown(rgbColor colorOne, rgbColor colorTwo)
{
  for (int i = 0; i < (8 * COG_SIZE); i++) {
    moveCogsDown(colorOne, colorTwo);
    showPixels();
    _delay(100);
  }
}

/* Show the Spartronics Blue and Yellow cogs! */
void cogs(rgbColor color1, rgbColor color2)
{
  cogs_init(color1, color2);
  while (1) {
    rotatingCogsUp(color1, color2);
    rotatingCogsDown(color1, color2);
  }
}

// COGS end ------------------

// Blink a single color on and off at the given time interval
void blink(unsigned long timeInterval, uint32_t color, uint8_t brightness)
{
  pixels.setBrightness(brightness);
  while (1)
  {
    setAllPixels(color);
    showPixels();
    delay(timeInterval);
    setAllPixelsOff();
    showPixels();
    delay(timeInterval);
  }
}


/*
 * Given a color fade in & out
 * Note: start and end values for k determines if color is ever off
 */
void fadeInOut(uint8_t red, uint8_t green, uint8_t blue, uint8_t wait, uint8_t maxBrightness)
{
  float r, g, b;

  // fade in: increase k intensity ratio
  for(int k = 0; k < maxBrightness; k=k+2)    // experiment w/ starting 'k' value
  {
    r = (k/256.0)*red;
    g = (k/256.0)*green;
    b = (k/256.0)*blue;
    setAllPixels(r,g,b);
    showPixels();
    delay(wait);
  }

  // fade out: decrease k intensity ratio
  for(int k = maxBrightness; k >= 0; k=k-2)  // experiment w/ ending 'k' value
  {
    r = (k/256.0)*red;
    g = (k/256.0)*green;
    b = (k/256.0)*blue;
    setAllPixels(r,g,b);
    showPixels();
    delay(wait);
  }
}

/**
 * Fade between two colors --
 *    - start w/ color 1 -- fade till all off
 *    - switch to color 2 -- bright till maxBrightness achieved
 */
void spartronics_fade(unsigned long wait, rgbColor color1, rgbColor color2, uint8_t maxBrightness)
{

  rgbColor currentColor;

  pixels.setBrightness(maxBrightness);  // needs to be passed in to the function

  while (1)
  {
    // start w/ blue in off to bright and off again
    currentColor = color1;
    fadeInOut(currentColor.r, currentColor.g, currentColor.b, wait, maxBrightness);
    
    // switch to yellof in off to bright and off again
    currentColor = color2;
    fadeInOut(currentColor.r, currentColor.g, currentColor.b, wait, maxBrightness);
  }
}
