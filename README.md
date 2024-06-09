Compiler Project Phase 3

================
INPUT TEST FILES : p3tests/
OUTPUT OF TEST FILES : p3tests_out/
================
The aim of the project is to correctly identyify the tokens in a given grammar. The project accepts the defined tokens and rejects the Illegal tokens.

The project contains the following files and below is the significance of each file,
grammar.cup file    --> contains definition of all terminals along with their Token number
tokens.jflex file   --> (a) defines the Regex for identifying the tokens correctly
                        (b) an object of Symbol class is created for each defined token
Makefile            --> Point of execution to test the tokens defined in the above files        
Steps to run the code:
1. To initiate the testing, execute the following command:
"""
        make run 
        OR make
"""

    The file will run input test cases for 3 test files basicTerminals.txt, basicRegex.txt, basicFails.txt and would store the output in their respective output files basicTerminals-output.txt, basicRegex-output.txt, basicFails-output.txt

2. To check other test cases, 

    (a) Create a new file <newTestcase.txt> and add the test cases that are needed to be tested.
    Make the following changes in the Makefile to give the new test file as an input. 
    Add the following command in Makefile for any newTestcase.txt
    """
            $(JAVA) -cp $(CP) LexerTest <newTestcase.txt> > <newTestcase-output.txt>
            cat <newTestcase.txt>
            cat -n <newTestcase-output.txt>
    """
To run the newly added test cases, repeat the step-1.
The acceptable and the non acceptable tokens can be viewed in the output files.





