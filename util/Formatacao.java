/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Formatacao {
  public static ArrayList<JTextArea> arrayCaixasDeTexto;
  private static Font fonte = new Font("txt", Font.BOLD, 12);
  private static JScrollPane barraDeRolagem;

  /* **************************************************************
  Metodo: inicializarLabelsDeTextos*
  Funcao: Formatar o labels de texto*
  Parametros: JTextArea txt: label a ser formatado
              int largura: largura
              int altura: altura*
  Retorno: void*
  *************************************************************** */
  public static void inicializarLabelsDeTextos(JTextArea txt, int largura, int altura) {
    txt.setPreferredSize(new Dimension(largura, altura));
    txt.setFont(fonte);
    txt.setEditable(false);
    txt.setAlignmentX(Component.LEFT_ALIGNMENT);
  }

  /* **************************************************************
  Metodo: inicializarBarraDeRolagem*
  Funcao: Criar as barras de rolagem*
  Parametros: JTextArea txt: textArea que se quer adicionar a barra
              int largura: largura
              int altura: altura*
  Retorno: void*
  *************************************************************** */
  public static JScrollPane inicializarBarraDeRolagem(JTextArea txt, int largura, int altura) {
    barraDeRolagem = new JScrollPane(txt){
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(largura, altura);
      }
    };

    return barraDeRolagem;
  }

  /* **************************************************************
  Metodo: criarCaixasDeTexto*
  Funcao: Criar as caixas de texto que exibem as informacoes em
          runtime*
  Parametros: nulo*
  Retorno: ArrayList<JTextArea> arrayCaixasDeTexto: array contendo
          todas as caixas de texto*
  *************************************************************** */
  public static ArrayList<JTextArea> inicializarCaixasDeTexto() {
    Formatacao.arrayCaixasDeTexto = new ArrayList<>();

    for(int i=0; i<7; i++){
      if(i == 0 || i == 1){
        arrayCaixasDeTexto.add(new JTextArea(){
          {
            this.setFont(fonte);
            this.setEditable(false);
            this.setLineWrap(true);
          }
        });
      }else{
        arrayCaixasDeTexto.add(new JTextArea(){
          {
            this.setFont(fonte);
            this.setEditable(false);
          }
        });
      }
    }

    return arrayCaixasDeTexto;
  }
}