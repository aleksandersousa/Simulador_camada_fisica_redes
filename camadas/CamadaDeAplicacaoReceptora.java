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
import view.PainelEsquerdo;

import util.Conversao;

public class CamadaDeAplicacaoReceptora {

  /* *****************************************************************************
  Metodo: camadaDeAplicacaoReceptora*
  Funcao: Converter o array de numeros ascii em string*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaDeAplicacaoReceptora(int[] quadro) {
    String mensagem = Conversao.asciiParaMensagem(quadro);
    CamadaDeAplicacaoReceptora.aplicacaoReceptora(mensagem);
  }

  /* *****************************************************************************
  Metodo: aplicacaoReceptora*
  Funcao: Imprimir a mensagem decodificada na tela*
  Parametros: String mensagem: mensagem a ser impressa*
  Retorno: void*
  ***************************************************************************** */
  public static void aplicacaoReceptora(String mensagem) {
    TelaPrincipal.imprimirNaTela(mensagem, TelaPrincipal.MENSAGEM_DECODIFICADA);

    PainelEsquerdo.cmbListaDeCodificacao.setEnabled(true); //re ativa o combobox

    PainelEsquerdo.mutex.release(); //libera o combobox
  }
}