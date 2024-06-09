/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Pascal
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./PascalLexerTest.java, q.v.  See 330 Lecture Notes 2 and the
 * Assignment 2 writeup for further discussion.
 *
 */


import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class Lexer

%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */

tab = \\t
newline = \\n
slash = \\ 
escapeapos = {slash}'
escapequote = {slash}\"
int = [0-9]+ 
float = ([0-9]+).[0-9]+
charchar		    = [[^\\]&&[^']]|{newline}|{tab}|{escapeapos}|{slash}{slash}
charlit     	  = '{charchar}'
letter = [[[^\n]&&[^\t]]&&[[^\\][^\"]]]|\\\\|\\\"
id = [a-zA-Z][a-zA-Z0-9]*
stringchar		  = [[[^\\]&&[^\"]]&&[[^\n]&&[^\t]]]|{newline}|{tab}|{escapequote}|{slash}{slash}
string		    = \"{stringchar}*\"
whitespace = [ \n\t\r]
singlelinecomment = \\.*(\r | \n | \r\n)
singleline = .*(\r | \n | \r\n)
blockcommentS   = {slash}\*
blockcommentE   = \*{slash}
//                A comment body character can be anything other than the end to
//                the block comment.
commentbody		  = ([^\*]|(\*+[^\\]))
blockcomment    = {blockcommentS}{commentbody}*?{blockcommentE}
inlinecomment 	= {slash}{slash}.*(\n|\r|\r\n)
whitespace      = [ \n\t\r]

/**
 * Implement patterns as regex here
 */



%%
/**
 * LEXICAL RULES:
 */

class           {return newSym(sym.CLASS, "class");}
"{"             {return newSym(sym.CURLY_OPEN, "{");}
"}"             {return newSym(sym.CURLY_CLOSE, "}");}
final           {return newSym(sym.FINAL, "final");}
"="             {return newSym(sym.EQUALS, "=");}
"("             {return newSym(sym.ROUND_OPEN, "(");}
")"             {return newSym(sym.ROUND_CLOSE, ")");}
";"             {return newSym(sym.SEMICOLON, ";");}
":"             {return newSym(sym.COLON, ";");}
","             {return newSym(sym.COMMA, ";");}
"["             {return newSym(sym.SQUARE_OPEN, "[");}
"]"             {return newSym(sym.SQUARE_CLOSE, "]");}
void            {return newSym(sym.VOID, "void");}
char		    { return newSym(sym.CHAR, "char"); }
int             {return newSym(sym.INT, "int");}
string          {return newSym(sym.STRING, "string");}
bool            {return newSym(sym.BOOL, "bool");}
float           {return newSym(sym.FLOAT, "float");}
true            {return newSym(sym.TRUE, "true");}
false           {return newSym(sym.FALSE, "false");}
"+"             {return newSym(sym.PLUS, "+");}
"?"             {return newSym(sym.QUESTION, "?");}
"-"             {return newSym(sym.MINUS, "-");}
"*"             {return newSym(sym.MULTIPLY, "*");}
"/"             {return newSym(sym.DIVIDE, "/");}
"~"             {return newSym(sym.NOT, "~");}
if              {return newSym(sym.IF, "if");}
else            {return newSym(sym.ELSE, "else");}
while           {return newSym(sym.WHILE, "while");}
read            {return newSym(sym.READ, "read");}
print           {return newSym(sym.PRINT, "print");}
printline       {return newSym(sym.PRINTLINE, "printline");}
return          {return newSym(sym.RETURN, "return");}
"++"            {return newSym(sym.INCR, "++");}
"--"            {return newSym(sym.DECR, "--");}
"<"             {return newSym(sym.LESSTHAN, "<");}
">"             {return newSym(sym.GREATERTHAN, ">");}
"<="            {return newSym(sym.LESSTHANEQUAL, "<=");}
">="            {return newSym(sym.GREATERTHANEQUAL, ">=");}
"=="            {return newSym(sym.EQUALSCHECK, "==");}
"<>"            {return newSym(sym.NOTEQUAL, "<>");}
"&&"            {return newSym(sym.AND, "&&");}
"||"            {return newSym(sym.OR, "||");}
{id}            {return newSym(sym.ID, yytext());}
{charlit}       { return newSym(sym.CHARLIT, yytext()); }
{int}           {return newSym(sym.INTLIT, new Integer(yytext()));}
{string}        {return newSym(sym.STRLIT, yytext());}
{float}         {return newSym(sym.FLOATLIT, new Float(yytext()));}
{inlinecomment} { /* For this stand-alone lexer, print out comments. */}
{blockcomment}	{ /* For this stand-alone lexer, print out comments. */}
{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); }

/**
 * Implement terminals here, ORDER MATTERS!
 */
 
{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); } 