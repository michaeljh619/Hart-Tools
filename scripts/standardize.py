import os
import sys

def getLeadingZeroes(number):
  if number < 10:
    return "00"
  elif number < 100:
    return "0"
  else:
    return ""

if len(sys.argv) != 3:
  print("\nError: Wrong number of command line args. " + str(len(sys.argv)))
  print("Usage: " + sys.argv[0] + " fileBeginning .extension")

else:
  fileBeginning = sys.argv[1]
  fileType = sys.argv[2]

  for filename in os.listdir("."):
    if filename.endswith(fileType) and filename.startswith(fileBeginning):
      # Strip file beginning and extension from file name
      newFileName = filename.replace(fileBeginning, "")
      newFileName = newFileName.replace(fileType, "")
      number = int(newFileName)

      # Leading Zero formatting
      leadingZeroes = getLeadingZeroes(number)
      os.rename(filename, leadingZeroes + str(number) + fileType)

  
