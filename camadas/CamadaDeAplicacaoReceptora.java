/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas;

import view.TelaPrincipal;
import util.Conversao;

public class CamadaDeAplicacaoReceptora {

  /* *****************************************************************************
  Metodo: camadaDeAplicacaoReceptora*
  Funcao: Imprimir a mensagem decodificada na tela*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaDeAplicacaoReceptora(int[] quadro) {
    TelaPrincipal.imprimirNaTela(Conversao.asciiParaMensagem(quadro), TelaPrincipal.MENSAGEM_DECODIFICADA);
  }
}