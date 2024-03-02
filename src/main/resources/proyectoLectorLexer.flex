package edu.cunoc.Instructor;

import java_cup.runtime.*;

%%

%class ProyectoLectorLexer
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

Space = {EOL} | [\t\f] | [\\ \\n\\r\\t\\f]

Id = (@ | - | "+" | "*" | _ | # | [:letter:] | [:digit:] )+

Direccion = (("/" {Id})+ | (C : (\\ {Id})+))  "." csv

%%

 "nombre"            {return symbol(sym.NOMBRE);}
 "ubicacion"         {return symbol(sym.UBICACION);}
 "PROYECTO"          {return symbol(sym.PROYECTO);}
 "ARCHIVO"           {return symbol(sym.ARCHIVO);}
 "CARPETA"           {return symbol(sym.CARPETA);}

 {Id}                {return symbol(sym.ID_ARCHIVO, new String(yytext()));}
 {Direccion}         {return symbol(sym.DIRECCION, new String(yytext()));}

 "="                 {return symbol(sym.IGUAL);}
 "<"                 {return symbol(sym.MENOSQUE);}
 ">"                 {return symbol(sym.MAYORQUE);}
 "/"                 {return symbol(sym.DIAGONAL);}
 \"                  {return symbol(sym.COMILLA);}

 {Space}             {                }

 <<EOF>>             {return symbol(sym.EOF);}

 [^]                 {error("Simbolo invalido <"+yytext()+"> en linea "+(yyline+1)+" en columna "+ (yycolumn+1));}
