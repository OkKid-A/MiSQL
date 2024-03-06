package edu.cunoc.LectorCSV;

import java_cup.runtime.*;

%%

%class CSVLexer
%cup
%line
%column
%public

%{

   private Symbol symbol(int type) {
       return new Symbol(type, yyline+1, yycolumn+1);
   }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        System.out.println("Error en linea "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
    }
%}

EOL = \n|\r|\r\n

Space = [ \t\f]

Entero = 0 | [1-9][0-9]*;
Decimal = {Entero}+ "." [1-9][0-9]*
Id = (@ | - | "+" | "*" | _ | # | [:letter:] | [:digit:]  | \")+ ({Space} (@ | - | "+" | "*" | _ | # | [:letter:] | [:digit:]  | \"))*
Otro = ({Entero}|{Decimal}|{Id}|{Space})+

%%

 {Id}                {return symbol(sym.ID, new String(yytext()));}
 {Decimal}            {return symbol(sym.DECIMAL, new Float(yytext()));}
 {Entero}            {return symbol(sym.ENTERO, new Integer(yytext()));}
 {Otro}            {return symbol(sym.OTRO, new String(yytext()));}

 ","                 {return symbol(sym.COMA);}
 {EOL}               {return symbol(sym.EOL);}

 {Space}                {}

 <<EOF>>             {return symbol(sym.EOF);}

 [^]                 {error("Simbolo invalido <"+yytext()+"> en linea "+(yyline+1)+" en columna "+ (yycolumn+1));}
