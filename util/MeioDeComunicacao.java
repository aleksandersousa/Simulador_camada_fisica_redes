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
      @Override
      public void run() {
        for(int i=0; i<fluxoBrutoDeBitsPontoA.length; i++){
          fluxoBrutoDeBitsPontoB[i] = fluxoBrutoDeBitsPontoA[i];
          Canvas.fluxoDeBits.add(fluxoBrutoDeBitsPontoB[i]);
        }

        Canvas.iniciarListaDeImagens();
        Canvas.flag = true;
        TelaPrincipal.repintarPainel();

        try {
          Canvas.trava.acquire();
        } catch (Exception e) {
          System.out.println("Erro no acquire do semaforo trava!");
        }

        CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
      }
    }).start();
  }
}