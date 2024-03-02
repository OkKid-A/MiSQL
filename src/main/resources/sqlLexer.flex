package edu.cunoc.Instructor;

import java_cup.runtime.*;

%%

%class sqlLexer
%cup
%line
%column

%{

   private Symbol symbol(int type) {
       return new Symbol(type, yyline+1, yycolumn+1);
   }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        System.out.println("Error en linea line "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
    }
%}


EOL = \n|\r|\r\n

Space = {EOL} | [\t\f]
Comilla = \"

Id = (@ | - | "+" | "*" | _ | # | [:letter:] | [:digit:] )+

%%

 "SELECCIONAR"               {return symbol(sym.SELECCIONAR);}
 "INSERTAR"                  {return symbol(sym.INSERTAR);}
 "ELIMINAR"                  {return symbol(sym.ELIMINAR);}
 "ACTUALIZAR"                {return symbol(sym.ACTUALIZAR);}
 "ASIGNAR"                   {return symbol(sym.ASIGNAR);}
 "FILTRAR"                   {return symbol(sym.FILTRAR);}
 "EN"                        {return symbol(sym.EN);}
 "VALORES"                   {return symbol(sym.VALORES);}
 "AND"                       {return symbol(sym.AND);}
 "OR"                        {return symbol(sym.OR);}

 {Id}                        {return symbol(sym.ID_ARCHIVO);}
 [:digit:]+                  {return symbol(sym.ENTERO);}
 [:digit:]+ "." [1-9][0-9]*  {return symbol(sym.ENTERO);}

 "<"">"                      {return symbol(sym.DISTINTO);}
 "<""="                      {return symbol(sym.MENORIGUAL);}
 ">""="                      {return symbol(sym.MAYORIGUAL);}
 "<"                         {return symbol(sym.MENORQUE);}
 ">"                         {return symbol(sym.MAYORQUE);}
 "="                         {return symbol(sym.IGUAL);}
 {Comilla}                   {return symbol(sym.COMILLA);}
 \;                          {return symbol(sym.PC);}
 \,                          {return symbol(sym.COMA);}
 \(                          {return symbol(sym.PAR_OP);}
 \)                          {return symbol(sym.PAR_CLO);}

 {Space}             {}
 <<EOF>>             {return symbol(sym.EOF);}

 [^]                 {error("Simbolo invalido <"+yytext()+"> en linea "+(yyline+1)+" en columna "+ (yycolumn+1));}