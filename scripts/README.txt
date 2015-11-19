Version: 1.0

Description: Using the steps in this README, you will be able to take multiple .png files and condense them into one .png file that can be sliced into TextureRegions. This can work with jpeg files as well, however png files actually benefit from multiple files being condensed into a single file, jpegs do not!


Steps:

  1.) Copy all your .png files into this folder that you wish to condense into a single .png file. Files that are brought in must follow some type of naming convention:
    - "walk1.png, walk2.png, walk3.png, ..."

  2.) Naming conventions with numbers at the end often result with an issue of sorting numbers, for example your files may get sorted:
    - "walk1.png, walk10.png, walk2.png, ...", if you had 10 pngs in your file
    - If you do not have this issue, skip ahead to Step 4.

  3.) To fix the problem in Step 2, we will need to run standardize.py from the command line as follows:
    - "$standardize.py fileBeginning .extension"
  So taking our previous example of walk1.png, walk2.png, ..., walk10.png; we would run standardize as follows:
    - "$standardize.py walk .png"
  Standardize will rename all the files that start with "walk" and end with ".png" and make their naming schemes as such:
    - "001.png" (previously "walk1.png")
    - "002.png" (previously "walk2.png")
    - "010.png" (previously "walk10.png")
  Currently standardize.py only works for up to 999 files, although that can be fixed by adding more leading zeroes in the .py file

  4.) Now we are ready for some magick! First, ensure that you have magick 
  installed on this system:
    - http://www.imagemagick.org/script/download.php

  5.) Now, through the command line, navigate to this folder and once here we will run magick's montage. Now at this point, make sure that the only png's in this folder are the ones you wish to convert into a single png. Here is how we will be using montage:
    - montage *.png args output.png
  Do not actually input the "args" portion, that will be where we input the arguments for montage for some special formatting. Here is what we can put in, separated by spaces and without the quotes, in the args section:
    - "-background none" : This ensures that you maintain a transparent background in your pngs
    - "-geometry +0+0"   : This ensures that your new .png will not resize your original pngs to smaller versions in the new png
    - "-tile 5x1"       : This will tile your pngs into a certain tile form, here we are doing 5 columns by 1 row
  So staying with our example before, we can run montage as so:
    - "$montage *.png -background none -geometry +0+0 -tile 10x1 output.png"
  If you followed all the steps correctly, you should have a 10x1 strip named output.png.