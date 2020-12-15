package othetoe;

public class Player {
   private String userId;
   private String userName;
   private String userImage;
   private int point = 0;
   private PowerUp[] powerUp = new PowerUp[4];
   public Player(String userId,String userName,String userImage){
      this.userId = userId;
      this.userName = userName;
      this.userImage = userImage;
      for (int i = 0; i<4; i++){
         powerUp[i] = new PowerUp(i);
      }
   }
   
   public String getUserId() {
      return userId;
   }
   public String getUserName() {
      return userName;
   }
   public String getUserImage() {
      return userImage;
   }
   public PowerUp getPowerUp(int powerUpId){
      return powerUp[powerUpId];
   }
   public int getPoint() {
      return point;
   }
   public void setPoint(int point) {
      this.point = point;
   }
}
