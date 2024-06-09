JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
		$(JAVAC) -cp $(CP) $*.java

FILE=    Lexer.java      parser.java    sym.java \
    LexerTest.java	 ScannerTest.java	 Program.java	 Stmt.java	 Token.java		Stmts.java	Readlist.java	Printlist.java	Expr.java	Args.java \
	Memberdecls.java	Members.java	Fielddecls.java	Fielddecl.java	Methoddecls.java	Methoddecl.java	TypeCheckingTest.java	\
	Argdecl.java	ArgdeclList.java	Argdecls.java	Argument.java	Binaryoperations.java	LangException.java	Name.java	\
	Printlinelist.java	SymbolTable.java		

run: basicTest.txt

all: Lexer.java parser.java $(FILE:java=class)

basicTest.txt: all
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badDec.as > p3tests_out/badDec-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badInc.as > p3tests_out/badInc-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badNegation.as > p3tests_out/badNegation-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badString.as > p3tests_out/badString-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badTernaryCond.as > p3tests_out/badTernaryCond-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/badTernaryTypes.as > p3tests_out/badTernaryTypes-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/boolToFloat.as > p3tests_out/boolToFloat-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/boolToInt.as > p3tests_out/boolToInt-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/callNonExistFunc.as > p3tests_out/callNonExistFunc-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/charToFloat.as > p3tests_out/charToFloat-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/charToInt.as > p3tests_out/charToInt-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/floatToInt.as > p3tests_out/floatToInt-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/fullValidProgram.as > p3tests_out/fullValidProgram-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/incompatBinary.as > p3tests_out/incompatBinary-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/intArrayToBoolArray.as > p3tests_out/intArrayToBoolArray-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/reassignFinal.as > p3tests_out/reassignFinal-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/redefMethod.as > p3tests_out/redefMethod-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/redefVar.as > p3tests_out/redefVar-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/redefVarAsMethod.as > p3tests_out/redefVarAsMethod-output.as
		$(JAVA) -cp $(CP) TypeCheckingTest p3tests/returnTypeBad.as > p3tests_out/returnTypeBad-output.as

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
