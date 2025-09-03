# Text Generator (Markov Chain)

A Java-based Markov Chain text generator built on a weighted directed graph.  
Model is trained on text files, utilizing them to build a weighted digraph(implemented from scratch by me! :)). 
Application can be run in the console and has 3 text generation modes. 

- **Probable** – outputs the *k* most likely next words, that come after the word given to the program.
- Example: Model trained on Shakespearean sonnets, given the word thy, and produces the 5 most probable words to come after thy. 
<img width="1121" height="26" alt="markov chain console output probable" src="https://github.com/user-attachments/assets/ed11dc7e-a675-4bec-a08f-cb7655f64ed4" />

- **Random** – Produces a sentence of specified length, selecting each next word randomly, weighted by probability.
- Example: Given the word then, the model produces a 20 word sentence, selecting the next random words.
- <img width="1144" height="41" alt="markov chain console output random" src="https://github.com/user-attachments/assets/d41c9e15-973e-476f-b23c-fce28e826b73" />

- **Deterministic** – Produces a sentence of specified length, always selects the single most probable next word.
- Example: Given the word flame, the model produces the a sentence of the 15 most probable next words, with no randomness.
<img width="1144" height="42" alt="markov chain console output deterministic" src="https://github.com/user-attachments/assets/0fc99b87-4649-46a9-88a2-b52b0c6ed6bb" />



---------- To Use ------------
Load program into console, feed the command line the name of the text training file, the root word, the number of words to be generated, and the mode of generation. 
Example: "TextFile.txt" this 10 probable
