# WordCreatorPhoneme
Simplified version of the word creator, using only phonemes.
Creates words with a certain consonance that you can customize

## How to use it ?
First, get an IDE (Eclipse)

1. Download Eclipse (The last version)
2. Install Eclipse
3. Launch Eclipse

Next, clone / download this project

Go to Eclipse and follow the procedure :

File -> Open project from File system -> Directory -> [go to the project] -> Select the project with the checkbox -> Finish

Then, you should have access to the project.

To launch it, you can :
1. click the run button
2. ctrl + f11
3. use the menu : Run -> Run

To change it : open the project from the Eclipse window -> open the src folder -> open the launcher package (folder) -> double click on Launcher.java

## RESOURCES
This project uses different resources files.

These files are stored in the resources folder of the WordCreator project.

You can update them as much as you want (adding/removing lines).

## How does it work ?
It works like a decision tree. When you have your first phoneme (like "a" in this example), the program will randomly choose one of the next phoneme that can fit. If two consonant or vowels are juxtaposed, we force the next one to be a vowel or a consonant. We won't find abberations like finding "mlfpxct" thanks to this method, but only words that could have the most chance to appears.

## Architecture
You will find only 3 javaclass :

First, the Launcher.java class : It's the entry point (the Main class) that's allow you to use different functions of the program.

Then, you have the WordAnalyzerManager.java class : The interface that will use the WordAnalyzer file to create random words.

And last, the WordAnalyzer.java class : the heart of this program. It will use an analysis to create new words.
