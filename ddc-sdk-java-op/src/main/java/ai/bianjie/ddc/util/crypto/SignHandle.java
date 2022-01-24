package ai.bianjie.ddc.util.crypto;

public class SignHandle {

     ISignHandle sign;

     public SignHandle(SignType st, String prvKey, String pubKey){
         switch (st){
             case SECP256K1:
                 try {
                     this.sign = new Secp256K1Handle(prvKey,pubKey);
                 } catch (Exception e) {
                    throw new SignException(e.toString());
                 }
                 break;
             default:
                 throw new SignException("Not implemented");
         }
         //signType = st;


     }

     public String Sign(byte[] data) throws Exception {
         if (this.sign == null ){
             throw new SignException("this sign is null");
         }

         byte[] s = this.sign.sign(data);
         //todo 返回格式化的签名字符串
         // HEX OR Base64
         return s.toString();
     }

     public boolean Verify(byte[] data, String mac) throws Exception {

         //todo 签名格式
         byte[] s = mac.getBytes();
         return this.sign.verify(data,s);
     }


     public String Address() throws Exception {
         return this.sign.getAddress();
     }


}


