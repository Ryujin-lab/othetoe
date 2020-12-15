package othetoe;

public class PowerUp {
   private int powerId;
   private int ammount = 1;

   public PowerUp(int powerId ){
      this.powerId = powerId;
   }

   public void setAmmount(int ammount) {
      this.ammount = ammount;
   }

   public int getAmmount() {
      return ammount;
   }

   public int getPowerId() {
      return powerId;
   }

}
