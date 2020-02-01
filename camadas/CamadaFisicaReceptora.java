/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas;

import view.PainelEsquerdo;
import view.TelaPrincipal;
import util.Conversao;

public class CamadaFisicaReceptora {
  private static int[] fluxoBrutoDeBits;

  /* *****************************************************************************
  Metodo: camadaFisicaReceptora*
  Funcao: Enviar o vetor com os numeros da tabela ASCII para a camada de aplicacao
          receptora*
  Parametros: int[] fluxoBrutoDeBitsPontoB: vetor com os bits recebidos*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB) {
    int tipoDeDecodificacao = PainelEsquerdo.cmbListaDeCodificacao.getSelectedIndex();

    //imprime todo o passo a passo na tela
    switch(tipoDeDecodificacao) {
      case 0: //decodificacao Binaria
        fluxoBrutoDeBits = CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoBinaria(fluxoBrutoDeBitsPontoB);
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
      case 1: //decodificacoa Manchester
        fluxoBrutoDeBits = CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoManchester(fluxoBrutoDeBitsPontoB);
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
      case 2: //decodificacao Manchester Diferencial
        fluxoBrutoDeBits = CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoManchesterDiferencial(fluxoBrutoDeBitsPontoB);
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
    }

    CamadaDeAplicacaoReceptora.camadaDeAplicacaoReceptora(fluxoBrutoDeBits);
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoBinaria*
  Funcao: Decodificar os bits da codificacao binaria*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoBinaria(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);
    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_DECODIFICADO);

    return Conversao.bitsParaAscii(fluxoBrutoDeBits);
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoManchester*
  Funcao: Decodificar os bits da codificacao manchester*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoManchester(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);

    int[] bitsDecodificados = new int[fluxoBrutoDeBits.length/2];
    for(int i=0, j=0; i<fluxoBrutoDeBits.length; i+=2){
      if(fluxoBrutoDeBits[i] == 1){
        bitsDecodificados[j] = 0;
        j++;
      }
      else{
        bitsDecodificados[j] = 1;
        j++;
      }
    }

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsDecodificados), TelaPrincipal.BIT_DECODIFICADO);

    return Conversao.bitsParaAscii(bitsDecodificados);
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoManchesterDiferencial*
  Funcao: Decodificar os bits da codificacao manchester diferencial*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoManchesterDiferencial(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);

    int[] bitsDecodificados = new int[fluxoBrutoDeBits.length/2];
    int cont = 0; //numero de vezes que o bit 1 repete
    int cont2 = 0; //numero de vezes que o bit 0 repete

    for(int i=0, j=1; i<fluxoBrutoDeBits.length; i+=2){
      if(i == 0){
        if(fluxoBrutoDeBits[0] == 1 && fluxoBrutoDeBits[1] == 0){
          bitsDecodificados[0] = 0;
          cont2++;
        }
        else{
          bitsDecodificados[0] = 1;
          cont++;
        }
      }else if(fluxoBrutoDeBits[i] == fluxoBrutoDeBits[i-1] && cont == 0){
        bitsDecodificados[j] = 1-bitsDecodificados[j-1];
        cont++;
        cont2 = 0;
        j++;
      }
      else if(fluxoBrutoDeBits[i] == fluxoBrutoDeBits[i-1] && cont != 0){
        bitsDecodificados[j] = bitsDecodificados[j-1];
        cont++;
        cont2 = 0;
        j++;
      }
      else if(fluxoBrutoDeBits[i] != fluxoBrutoDeBits[i-1] && cont2 == 0)
      {
        bitsDecodificados[j] = 1-bitsDecodificados[j-1];
        cont = 0;
        cont2++;
        j++;
      }
      else if(fluxoBrutoDeBits[i] != fluxoBrutoDeBits[i-1] && cont2 != 0){
        bitsDecodificados[j] = bitsDecodificados[j-1];
        cont = 0;
        cont2++;
        j++;
      }
    }

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsDecodificados), TelaPrincipal.BIT_DECODIFICADO);

    return Conversao.bitsParaAscii(bitsDecodificados);
  }
}