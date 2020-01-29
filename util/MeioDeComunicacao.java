/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
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
  Funcao: Enviar os bits recebidos da camada fisica transmissora para a camada fisica receptora*
  Parametros: int[] fluxoBrutoDeBits: vetor com os os bits*
  Retorno: void*
  ***************************************************************************** */
  public static void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    for(int i=0; i<fluxoBrutoDeBitsPontoA.length; i++){
      fluxoBrutoDeBitsPontoB[i] = fluxoBrutoDeBitsPontoA[i];
    }

    //Pintando painel com os bits
    Canvas.fluxoDeBits = fluxoBrutoDeBitsPontoB;
    Canvas.flag = true;
    TelaPrincipal.repintarPainel();

    CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
  }
}