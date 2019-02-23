# SD2Lab8
This program is a image manipulation program. 
- Open button will open a file chooser that only allows the user to choose an image file.
- Save button allows the user to save the photo with a specified picture extension of png, jpg, gif, and tiff.
      - There are two other files types. The msoe format which is a text based file.
      - Which lookes like this
      
      - MSOE                    //File type
      - 3 3                     //Width and Height
      - #000000 #FFFFFF #000000 // Color for the first row of pixels.
      - #FFFFFF #FFFFFF #FFFFFF // Color for the second row of pixels.
      - #000000 #FFFFFF #000000 // Color for the third row of pixels.
      
     - The bmsoe is also a texted based file but is in bytes.
      - BMSOE  À  à   // File type, width and height in bytes.
      - // Then special characters for the color of the pixel in bytes.
      
- Reload button returns the shown image to the image that was first loaded.
- GrayScale button turns the image into the grayscale of this image, by going through each pixel and calculating the grayscale for each pixel.
- Negative button turn the image into the negative of this image, by going through each pixel and subtracting one by the red, one by the green, and one by the blue.
- Red button turn the image into the red component of each pixel, by going through each pixel and setting the green, and blue to zero.
- Red-Gray turns the odd rows of pixels of this image into Red and the even rows into grayscale, by using the calculation for each of the transformations.
- Show Filter button opens a new window that allows the user to sharpen, blur or custom filter to the image. 

This program was first down in object oriented programming than converted into functional programming. All of the code that is commented out is the object oriented programming.
