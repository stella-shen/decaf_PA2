//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short DOUBLE=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short REPEAT=286;
public final static short UNTIL=287;
public final static short UMINUS=288;
public final static short EMPTY=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   28,   27,   27,   26,   26,   29,   29,   29,   16,   17,
   21,   20,   15,   30,   30,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    1,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    3,    3,    1,    4,    5,    5,
    1,    1,    1,    1,    0,    3,    1,    4,    5,    9,
    7,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    8,    9,    6,   10,    0,    7,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   73,   67,    0,    0,
    0,    0,   82,    0,    0,    0,    0,   72,    0,    0,
    0,   25,    0,   28,   36,   26,    0,   30,   31,   32,
    0,    0,    0,   37,    0,    0,    0,    0,   48,    0,
    0,    0,   46,    0,   47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   33,   34,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   65,   66,    0,   62,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   68,    0,
    0,    0,   88,    0,    0,   44,    0,    0,   79,    0,
    0,   69,    0,    0,   70,    0,    0,   45,    0,    0,
   83,   68,    0,    0,   84,   81,    0,   80,
};
final static short yydgoto[] = {                          2,
    3,    4,   64,   21,   34,    8,   11,   23,   35,   36,
   65,   46,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   83,   76,   85,   78,  157,   79,  167,  124,  171,
};
final static short yysindex[] = {                      -253,
 -268,    0, -253,    0, -241,    0, -244,  -88,    0,    0,
   93,    0,    0,    0,    0, -238,    0, -148,    0,    0,
  -18,  -87,    0,    0,  -85,    0,    5,  -36,   18, -148,
    0, -148,    0,  -74,   22,   17,   23,    0,  -49, -148,
  -49,    0,    0,    0,    0,   -2,    0,    0,   35,   49,
   53,  571,    0,   40,   54,   59,   75,    0,  571,  571,
  527,    0,   20,    0,    0,    0,   58,    0,    0,    0,
   66,   67,   74,    0,   77,  460,    0, -142,    0,  571,
  571,  571,    0,  460,    0,   96,   50,  587,  101,  103,
  -43,  -43, -132,  286, -136,    0,    0,    0,    0,  571,
  571,  571,  571,  571,  571,  571,  571,  571,  571,  571,
  571,  571,  571,    0,  571,  112,  378,  111,  402,  121,
  565,  411,  460,  -19,    0,    0,  130,    0,  134,  460,
  499,  492,  342,  342,  -32,  -32,    9,    9,  -43,  -43,
  -43,  342,  342,  366,  587,   20,  571,   20,    0,  428,
  136,  571,    0,  571,  571,    0,  137,  133,    0,  439,
  -90,    0,  142,  460,    0,  460,  144,    0,  571,   20,
    0,    0,  127,  146,    0,    0,   20,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  188,    0,   68,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,    0,    0,  159,
    0,  159,    0,    0,    0,  160,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,  -75,  -75,
  -75,    0,  -58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  471,    0,   36,    0,    0,  -75,
  -58,  -75,    0,  145,    0,    0,    0,  -75,    0,    0,
   60,   87,    0,    0,    0,    0,    0,    0,    0,  -75,
  -75,  -75,  -75,  -75,  -75,  -75,  -75,  -75,  -75,  -75,
  -75,  -75,  -75,    0,  -75,   25,    0,    0,    0,    0,
  -75,    0,    6,    0,    0,    0,    0,    0,    0,  -23,
   47,  -17,  334,  358,  317,  623,  532,  541,  113,  122,
  152,  419,  450,    0,  -25,  -58,  -75,  -58,    0,    0,
    0,  -75,    0,  -75,  -75,    0,    0,  164,    0,    0,
  -33,    0,    0,    8,    0,  166,    0,    0,   -1,  -58,
    0,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  205,  198,  -11,   26,    0,    0,    0,  178,    0,
   -7,    0,   -9,  -61,    0,    0,    0,    0,    0,    0,
    0,  469,  805,  705,    0,    0,    0,    0,   71,    0,
};
final static int YYTABLESIZE=960;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   40,   87,  114,   28,  111,   28,   85,    1,    5,  109,
  107,   85,  108,  114,  110,   75,   28,   38,   33,  118,
   33,  153,    7,   61,  152,   85,   61,  113,   44,  112,
   60,   43,    9,   45,   10,   38,   22,   61,   24,   40,
   26,   61,   59,   25,   30,  111,   77,  115,   76,   77,
  109,   76,   60,   95,  114,  110,   31,   32,  115,   61,
   40,   43,   39,   41,   59,   43,   43,   43,   43,   43,
   43,   43,   47,   42,   80,   61,   39,   47,   47,   87,
   47,   47,   47,   43,   43,   43,   43,   60,   81,   85,
   60,   85,   82,   88,   39,   47,   63,   47,   89,  115,
   63,   63,   63,   63,   63,   60,   63,  174,   12,   13,
   14,   15,   16,   17,   90,   43,   96,   43,   63,   63,
   42,   63,   62,   64,   97,   98,   47,   64,   64,   64,
   64,   64,   99,   64,  116,  120,  159,  100,  161,   60,
  121,  125,   42,  126,  127,   64,   64,   87,   64,   51,
  129,  145,   63,   51,   51,   51,   51,   51,   52,   51,
  175,  149,   52,   52,   52,   52,   52,  178,   52,  147,
  154,   51,   51,  155,   51,  163,  152,  168,  170,   64,
   52,   52,  172,   52,  173,  176,  177,    1,   53,   27,
   15,   29,   53,   53,   53,   53,   53,    5,   53,   20,
   19,   42,   38,   86,   74,   51,   71,    6,   20,   37,
   53,   53,    0,   53,   52,  158,    0,   19,   42,   42,
    0,    0,    0,   85,   85,   85,   85,   85,   85,   85,
    0,   85,   85,   85,   85,    0,   85,   85,   85,   85,
   85,   85,   85,   85,   53,    0,    0,    0,    0,  103,
  104,   42,   85,   85,   12,   13,   14,   15,   16,   17,
   47,   61,   48,   49,   50,   51,    0,   52,   53,   54,
   55,   56,   57,   58,    0,   42,   12,   13,   14,   15,
   16,   17,   47,   63,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,   12,   13,   14,   15,
   16,   17,   43,   43,    0,   63,   43,   43,   43,   43,
    0,    0,    0,   47,   47,    0,   86,   47,   47,   47,
   47,    0,  111,    0,   60,   60,  128,  109,  107,    0,
  108,  114,  110,    0,    0,    0,    0,   63,   63,    0,
    0,   63,   63,   63,   63,  113,    0,  112,    0,   12,
   13,   14,   15,   16,   17,    0,    0,   54,    0,    0,
   54,    0,    0,    0,   64,   64,    0,    0,   64,   64,
   64,   64,   18,    0,   58,   54,  115,   58,  111,    0,
    0,    0,    0,  109,  107,    0,  108,  114,  110,    0,
   51,   51,   58,    0,   51,   51,   51,   51,   59,   52,
   52,   59,  111,   52,   52,   52,   52,  109,  107,   54,
  108,  114,  110,    0,  111,    0,   59,    0,  146,  109,
  107,    0,  108,  114,  110,  113,   58,  112,    0,   53,
   53,    0,  115,   53,   53,   53,   53,  113,  111,  112,
    0,    0,  148,  109,  107,    0,  108,  114,  110,    0,
   59,    0,    0,    0,    0,    0,  115,    0,  156,   57,
    0,  113,   57,  112,  111,    0,    0,    0,  115,  109,
  107,    0,  108,  114,  110,  111,    0,   57,    0,    0,
  109,  107,    0,  108,  114,  110,    0,  113,    0,  112,
   56,    0,  115,   56,    0,    0,  111,  169,  113,    0,
  112,  109,  107,    0,  108,  114,  110,   46,   56,    0,
    0,   57,   46,   46,   75,   46,   46,   46,  115,  113,
  162,  112,    0,    0,    0,    0,    0,    0,  111,  115,
   46,   75,   46,  109,  107,  111,  108,  114,  110,    0,
  109,  107,   56,  108,  114,  110,    0,    0,    0,   75,
  115,  113,    0,  112,    0,    0,    0,    0,  113,   60,
  112,   46,    0,  101,  102,    0,   61,  103,  104,  105,
  106,   59,   49,    0,   49,   49,   49,    0,    0,    0,
    0,   50,  115,   50,   50,   50,    0,    0,    0,  115,
   49,   49,    0,   49,   54,   54,    0,   60,    0,   50,
   50,    0,   50,   60,   61,    0,    0,    0,    0,   59,
   61,   58,   58,    0,   75,   59,   75,   58,   58,   60,
    0,    0,    0,    0,   49,    0,   61,    0,    0,    0,
    0,   59,    0,   50,    0,   59,   59,   75,   75,    0,
    0,   59,   59,  101,  102,   75,    0,  103,  104,  105,
  106,    0,    0,    0,    0,  101,  102,   31,    0,  103,
  104,  105,  106,   55,    0,    0,   55,   12,   13,   14,
   15,   16,   17,    0,    0,    0,    0,    0,    0,  101,
  102,   55,    0,  103,  104,  105,  106,  151,    0,    0,
    0,    0,    0,    0,    0,    0,   57,   57,    0,    0,
    0,    0,   57,   57,    0,  101,  102,    0,    0,  103,
  104,  105,  106,    0,    0,   55,  101,  102,    0,    0,
  103,  104,  105,  106,    0,    0,    0,   56,   56,    0,
    0,    0,    0,   56,   56,    0,    0,  101,  102,    0,
    0,  103,  104,  105,  106,    0,    0,    0,   46,   46,
   77,    0,   46,   46,   46,   46,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   77,    0,  101,
    0,    0,    0,  103,  104,  105,  106,    0,    0,    0,
  103,  104,  105,  106,    0,   77,    0,   93,    0,   47,
    0,   48,    0,    0,    0,    0,    0,    0,   54,    0,
   56,   57,   58,    0,    0,    0,    0,    0,    0,   49,
   49,    0,    0,   49,   49,   49,   49,    0,   50,   50,
    0,    0,   50,   50,   50,   50,    0,   47,    0,   48,
    0,    0,    0,   47,    0,   48,   54,    0,   56,   57,
   58,    0,   54,    0,   56,   57,   58,    0,    0,   47,
   77,   48,   77,    0,    0,    0,   84,    0,  122,    0,
   56,   57,   58,   91,   92,   94,    0,    0,    0,    0,
    0,    0,    0,   77,   77,    0,    0,    0,    0,    0,
    0,   77,    0,    0,  117,    0,  119,    0,    0,    0,
    0,    0,  123,    0,    0,    0,    0,    0,    0,    0,
   55,   55,    0,    0,  130,  131,  132,  133,  134,  135,
  136,  137,  138,  139,  140,  141,  142,  143,    0,  144,
    0,    0,    0,    0,    0,  150,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  123,
    0,  160,    0,    0,    0,    0,  164,    0,  165,  166,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   46,   91,   37,   91,   40,  261,  277,   42,
   43,   45,   45,   46,   47,   41,   91,   41,   30,   81,
   32,   41,  264,   41,   44,   59,   44,   60,   40,   62,
   33,   39,  277,   41,  123,   59,   11,   40,  277,   41,
   59,   59,   45,   18,   40,   37,   41,   91,   41,   44,
   42,   44,   33,   63,   46,   47,   93,   40,   91,   40,
   44,   37,   41,   41,   45,   41,   42,   43,   44,   45,
   46,   47,   37,  123,   40,   93,   41,   42,   43,   54,
   45,   46,   47,   59,   60,   61,   62,   41,   40,  123,
   44,  125,   40,   40,   59,   60,   37,   62,   40,   91,
   41,   42,   43,   44,   45,   59,   47,  169,  257,  258,
  259,  260,  261,  262,   40,   91,   59,   93,   59,   60,
  123,   62,  125,   37,   59,   59,   91,   41,   42,   43,
   44,   45,   59,   47,  277,   40,  146,   61,  148,   93,
   91,   41,  123,   41,  277,   59,   60,  122,   62,   37,
  287,   40,   93,   41,   42,   43,   44,   45,   37,   47,
  170,   41,   41,   42,   43,   44,   45,  177,   47,   59,
   41,   59,   60,   40,   62,   40,   44,   41,  269,   93,
   59,   60,   41,   62,   41,   59,   41,    0,   37,  277,
  123,  277,   41,   42,   43,   44,   45,   59,   47,   41,
   41,  277,  277,   59,   41,   93,   41,    3,   11,   32,
   59,   60,   -1,   62,   93,  145,   -1,  125,  277,  277,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,   93,   -1,   -1,   -1,   -1,  282,
  283,  277,  286,  287,  257,  258,  259,  260,  261,  262,
  263,  279,  265,  266,  267,  268,   -1,  270,  271,  272,
  273,  274,  275,  276,   -1,  277,  257,  258,  259,  260,
  261,  262,  263,  286,  265,  266,  267,  268,   -1,  270,
  271,  272,  273,  274,  275,  276,  257,  258,  259,  260,
  261,  262,  278,  279,   -1,  286,  282,  283,  284,  285,
   -1,   -1,   -1,  278,  279,   -1,  277,  282,  283,  284,
  285,   -1,   37,   -1,  278,  279,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   60,   -1,   62,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,  280,   -1,   41,   59,   91,   44,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
  278,  279,   59,   -1,  282,  283,  284,  285,   41,  278,
  279,   44,   37,  282,  283,  284,  285,   42,   43,   93,
   45,   46,   47,   -1,   37,   -1,   59,   -1,   41,   42,
   43,   -1,   45,   46,   47,   60,   93,   62,   -1,  278,
  279,   -1,   91,  282,  283,  284,  285,   60,   37,   62,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   41,
   -1,   60,   44,   62,   37,   -1,   -1,   -1,   91,   42,
   43,   -1,   45,   46,   47,   37,   -1,   59,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,
   41,   -1,   91,   44,   -1,   -1,   37,   59,   60,   -1,
   62,   42,   43,   -1,   45,   46,   47,   37,   59,   -1,
   -1,   93,   42,   43,   46,   45,   46,   47,   91,   60,
   93,   62,   -1,   -1,   -1,   -1,   -1,   -1,   37,   91,
   60,   63,   62,   42,   43,   37,   45,   46,   47,   -1,
   42,   43,   93,   45,   46,   47,   -1,   -1,   -1,   81,
   91,   60,   -1,   62,   -1,   -1,   -1,   -1,   60,   33,
   62,   91,   -1,  278,  279,   -1,   40,  282,  283,  284,
  285,   45,   41,   -1,   43,   44,   45,   -1,   -1,   -1,
   -1,   41,   91,   43,   44,   45,   -1,   -1,   -1,   91,
   59,   60,   -1,   62,  278,  279,   -1,   33,   -1,   59,
   60,   -1,   62,   33,   40,   -1,   -1,   -1,   -1,   45,
   40,  278,  279,   -1,  146,   45,  148,  284,  285,   33,
   -1,   -1,   -1,   -1,   93,   -1,   40,   -1,   -1,   -1,
   -1,   45,   -1,   93,   -1,  278,  279,  169,  170,   -1,
   -1,  284,  285,  278,  279,  177,   -1,  282,  283,  284,
  285,   -1,   -1,   -1,   -1,  278,  279,   93,   -1,  282,
  283,  284,  285,   41,   -1,   -1,   44,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   59,   -1,  282,  283,  284,  285,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,  284,  285,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   93,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,  278,  279,   -1,
   -1,   -1,   -1,  284,  285,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,  278,  279,
   46,   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   63,   -1,  278,
   -1,   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
  282,  283,  284,  285,   -1,   81,   -1,  261,   -1,  263,
   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,   -1,
  274,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,  263,   -1,  265,
   -1,   -1,   -1,  263,   -1,  265,  272,   -1,  274,  275,
  276,   -1,  272,   -1,  274,  275,  276,   -1,   -1,  263,
  146,  265,  148,   -1,   -1,   -1,   52,   -1,  272,   -1,
  274,  275,  276,   59,   60,   61,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  169,  170,   -1,   -1,   -1,   -1,   -1,
   -1,  177,   -1,   -1,   80,   -1,   82,   -1,   -1,   -1,
   -1,   -1,   88,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  100,  101,  102,  103,  104,  105,
  106,  107,  108,  109,  110,  111,  112,  113,   -1,  115,
   -1,   -1,   -1,   -1,   -1,  121,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  145,
   -1,  147,   -1,   -1,   -1,   -1,  152,   -1,  154,  155,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=289;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","DOUBLE","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"REPEAT","UNTIL","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : DOUBLE",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : RepeatStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"BoolExpr : Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"ExprList : NEW IDENTIFIER '(' ')'",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"RepeatStmt : REPEAT Stmt UNTIL '(' BoolExpr ')' ';'",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 441 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 575 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 53 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 59 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 63 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 73 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 79 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 83 "Parser.y"
{
                    	yyval.type = new Tree.TypeIdent(Tree.DOUBLE, val_peek(0).loc);
                    }
break;
case 8:
//#line 87 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 9:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 10:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 11:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 103 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 109 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 115 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 119 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 125 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 133 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 148 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 152 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 159 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 169 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 175 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 179 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 186 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 191 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 207 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 211 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 215 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 222 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 228 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 235 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 241 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 256 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 260 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 264 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 268 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 276 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 280 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 284 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 308 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 63:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 66:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 68:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 69:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 70:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 71:
//#line 346 "Parser.y"
{
						yyval.expr = val_peek(0).expr;
					}
break;
case 72:
//#line 352 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 73:
//#line 356 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 75:
//#line 363 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 76:
//#line 370 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 77:
//#line 374 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 78:
//#line 379 "Parser.y"
{
                		
                	}
break;
case 79:
//#line 385 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 80:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 81:
//#line 397 "Parser.y"
{
				  		yyval.stmt = new Tree.RepeatLoop(val_peek(2).expr, val_peek(5).stmt, val_peek(6).loc);
				  	}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1180 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
