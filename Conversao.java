public class Conversao {

   /* **************************************************************
  Metodo: bitsParaAscii*
  Funcao: Transforma os bits da mensagem em caracteres ASCII*
  Parametros: int[] fluxoBrutoDeBits: bits a serem convertidos*
  Retorno: int[] quadro: vetor com os numeros ASCII*
  *************************************************************** */
  public static int[] bitsParaAscii(int[] fluxoBrutoDeBits) {
    int tamanho = 0;

    //pegando tamanho do array de quadros
    for(int i=0; i<fluxoBrutoDeBits.length; i++){
      if(i % 8 == 0){
        tamanho++;
      }
    }

    //convertendo para string
    StringBuilder[] strQuadro = new StringBuilder[tamanho];
    int j=0;
    strQuadro[j] = new StringBuilder();
    for(int i=0; i<fluxoBrutoDeBits.length; i++){
      if(i % 8 == 0 && i != 0){
        strQuadro[++j] = new StringBuilder();
        strQuadro[j].append(fluxoBrutoDeBits[i]);
      }
      else{
        strQuadro[j].append(fluxoBrutoDeBits[i]);
      }
    }

    //convertendo os bits para inteiros da tabela ascii
    int[] quadro = new int[tamanho];
    for(int i=0; i<quadro.length; i++){
      quadro[i] = Integer.parseInt(strQuadro[i].toString(), 2);
    }

    return quadro;
  }

   /* **************************************************************
  Metodo: asciiParaBits*
  Funcao: Transforma os numeros ASCII para bits*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: int[] bitsBrutos: vetor com os bits*
  *************************************************************** */
  public static int[] asciiParaBits(int[] quadro) {
    StringBuilder strBitsBrutos = new StringBuilder();

    for(int i=0; i<quadro.length; i++){
      int val = quadro[i];

      for(int j=0; j<8; j++){
        strBitsBrutos.append((val & 128) == 0 ? 0 : 1);
        val <<= 1;
      }
    }

    int[] bitsBrutos = new int[strBitsBrutos.length()];
    for(int i=0; i<bitsBrutos.length; i++){
      bitsBrutos[i] = Character.getNumericValue(strBitsBrutos.charAt(i));
    }

    return bitsBrutos;
  }

   /* **************************************************************
  Metodo: asciiParaMensagem*
  Funcao: Transforma os numeros ASCII para Mensagem*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: String strMensagem: mensagem*
  *************************************************************** */
  public static String asciiParaMensagem(int[] quadro) {
    StringBuilder strMensagem = new StringBuilder();

    for(int i=0; i<quadro.length; i++){
       strMensagem.append(Character.toString((char)quadro[i]));
    }

    return strMensagem.toString();
  }

   /* **************************************************************
  Metodo: bitsParaString*
  Funcao: Transforma os os bits da mensagem para o tipo String*
  Parametros: int[] bits: vetor com os bits*
  Retorno: String strBits: String com os bits*
  *************************************************************** */
  public static String bitsParaString(int[] bits) {
    StringBuilder strBits = new StringBuilder();
    strBits.append(bits[0]);

    int i=1;
    for(; i<bits.length; i++){
      if(i % 8 == 0){
        strBits.append(" ");
        strBits.append(bits[i]);
      }
      else{
        strBits.append(bits[i]);
      }
    }

    return strBits.toString();
  }
}