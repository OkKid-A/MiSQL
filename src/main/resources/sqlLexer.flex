package edu.cunoc.Instructor;

import java_cup.runtime.*;

%%

%class SQLexer
%public
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
        System.out.println("Error lexico en linea "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
    }
%}


EOL = \n|\r|\r\n

Space = {EOL} | [\t\f]
SpaceMid = [ \t\f]

Entero = 0 | [1-9][0-9]*
Decimal = {Entero}+ "." [1-9][0-9]*
Id = (@ | - | "+" | "*" | _ | # | [:letter:] | {Entero} )+
IdExt =   \"{Id}+ ({SpaceMid} {Id})*\"
Otro = ({Entero}|{Decimal}|{Id}|{IdExt})+

%%

 "ELIMINAR"                  {return symbol(sym.ELIMINAR);}
 "SELECCIONAR"               {return symbol(sym.SELECCIONAR);}
 "INSERTAR"                  {return symbol(sym.INSERTAR);}
 "ACTUALIZAR"                {return symbol(sym.ACTUALIZAR);}
 "ASIGNAR"                   {return symbol(sym.ASIGNAR);}
 "FILTRAR"                   {return symbol(sym.FILTRAR);}
 "EN"                        {return symbol(sym.EN);}
 "VALORES"                   {return symbol(sym.VALORES);}
 "AND"                       {return symbol(sym.AND);}
 "OR"                        {return symbol(sym.OR);}
       "*"                         {return symbol(sym.ASTERISCO);}

 {Entero}                    {return symbol(sym.ENTERO, new Integer(yytext()));}
 {Decimal}                   {return symbol(sym.DECIMAL, new Float(yytext()));}
 {Id}                        {return symbol(sym.ID_ARCHIVO, new String(yytext()));}
 {IdExt}                     {return symbol(sym.ID, new String(yytext()));}
 {Otro}                      {return symbol(sym.OTRO, new String(yytext()));}


 "<"">"                      {return symbol(sym.DISTINTO);}
 "<""="                      {return symbol(sym.MENORIGUAL);}
 ">""="                      {return symbol(sym.MAYORIGUAL);}
 "<"                         {return symbol(sym.MENORQUE);}
 ">"                         {return symbol(sym.MAYORQUE);}
 "="                         {return symbol(sym.IGUAL);}
 \"                          {return symbol(sym.COMILLA);}
 \;                          {return symbol(sym.PC);}
 \,                          {return symbol(sym.COMA);}
 \(                          {return symbol(sym.PAR_OP);}
 \)                          {return symbol(sym.PAR_CLO);}
 \.                          {return symbol(sym.PUNTO);}
 {Space}             {             }
 <<EOF>>             {return symbol(sym.EOF);}

 [^]                 {error("Simbolo invalido <"+yytext()+"> en linea "+(yyline+1)+" en columna "+ (yycolumn+1));}