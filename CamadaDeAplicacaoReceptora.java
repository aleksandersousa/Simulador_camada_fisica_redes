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