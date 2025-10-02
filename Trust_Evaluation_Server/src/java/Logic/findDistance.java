/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author sumit saha
 */
public class findDistance {
    
    
  public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
  double theta = lon1 - lon2;
  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
  dist = Math.acos(dist);
  dist = rad2deg(dist);
  dist = dist * 60 * 1.1515;
  if (unit == 'K') {
    dist = dist * 1.609344;
  } else if (unit == 'N') {
  	dist = dist * 0.8684;
 }
 return (dist);
}
  
private double deg2rad(double deg) {
  return (deg * Math.PI / 180.0);
}

private double rad2deg(double rad) {
  return (rad * 180 / Math.PI);
}

    public static void main(String[] args) {
       findDistance fd=new findDistance();
       double dis=fd.distance(12.9166251, 77.6010273, 12.9165802, 77.6010215, 'K');
       System.out.println("K>>"+dis);
        
    }

}
