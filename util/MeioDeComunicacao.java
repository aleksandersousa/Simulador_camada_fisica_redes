/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package util;

import view.Canvas;
import view.TelaPrincipal;
import camadas.CamadaFisicaReceptora;

public class MeioDeComunicacao {

  /* *****************************************************************************
  Metodo: meioDeComunicacao*
  Funcao: Enviar os bits recebidos da camada fisica transmissora para a camada
          fisica receptora*
  Parametros: int[] fluxoBrutoDeBits: vetor com os os bits*
  Retorno: void*
  ***************************************************************************** */
  public static void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    new Thread(new Runnable() {
      int valor = 0;
      int displayMask = 1 << 31;

      @Override
      public void run() {

        //passando bit a bit de um vetor para outro
        for(int i=0; i<fluxoBrutoDeBitsPontoA.length; i++){
          int numero = fluxoBrutoDeBitsPontoA[i];

          for(int j=1; j<=32; j++){
            if((numero & displayMask) == 0){
              valor <<= 1;
              valor = valor | 0;
            }else{
              valor <<= 1;
              valor = valor | 1;
            }
            numero <<= 1;
          }

          fluxoBrutoDeBitsPontoB[i] = valor;
          valor = 0;
        }

        //passando os bits para o canvas
        String strBits = Conversao.bitsParaString(fluxoBrutoDeBitsPontoB);
        for(int i=0; i<strBits.length(); i++){
          if(Character.getNumericValue(strBits.charAt(i)) != -1){ //nao pega o espaco
            Canvas.fluxoDeBits.add(Character.getNumericValue(strBits.charAt(i)));
          }
        }

        //inciando animacao
        Canvas.iniciarListaDeImagens();
        Canvas.flag = true;
        TelaPrincipal.repintarPainel();

        try {
          Canvas.trava.acquire(); //trava essa thread ate o canvas terminar a animacao
        } catch (InterruptedException e) {
          System.out.println("Erro no acquire do semaforo trava!");
        }

        CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
      }
    }).start();
  }
}