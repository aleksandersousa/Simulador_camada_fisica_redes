public class CamadaDeAplicacaoTransmissora {

  /* *****************************************************************************
  Metodo: camadaDeAplicacaoTransmissora*
  Funcao: Tranformar a mensagem em caracteres ASCII e enviar para a camada fisica transmissora*
  Parametros: String mensagem: mensagem a ser enviada*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaDeAplicacaoTransmissora(String mensagem) {
    int[] quadro = new int[mensagem.length()];
    for(int i=0; i<mensagem.length(); i++){
      quadro[i] = mensagem.charAt(i);
    }

    CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro);
  }
}