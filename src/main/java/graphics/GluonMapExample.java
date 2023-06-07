package graphics;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GluonMapExample {

  private static MapView mapView;
  private static List<MapLayer> mapLayers = new ArrayList<>();

  public static VBox displayMap() {

    Stage stage = new Stage();

    /* Définit la plate-forme pour éviter "javafx.platform is not defined" */
    System.setProperty("javafx.platform", "desktop");

    /*
     * Définit l'user agent pour éviter l'exception
     * "Server returned HTTP response code: 403"
     */
    System.setProperty("http.agent", "Gluon Mobile/1.0.3");

    VBox root = new VBox();

    /* Création de la carte Gluon JavaFX */
    MapView mapView = new MapView();

    /* Création du point avec latitude et longitude */
    MapPoint mapPoint = new MapPoint(46.227638, 2.213749);

    /* Création et ajoute une couche à la carte */

    // MapLayer mapLayer = new CustomPinLayer(mapPoint);
    // MapLayer mapLayer = new CustomCircleMarkerLayer(mapPoint);
    // mapView.addLayer(mapLayer);

    /* Zoom de 5 */
    mapView.setZoom(5);

    /* Centre la carte sur le point */
    mapView.flyTo(0, mapPoint, 0.1);

    mapView.setMinSize(500, 500);
    mapView.setMaxSize(500, 500);

    root.getChildren().add(mapView);

    return root;
  }

  public static void addMarker(MapPoint mapPoint) {
    MapLayer mapLayer = new CustomCircleMarkerLayer(mapPoint);
    mapView.addLayer(mapLayer);
    mapLayers.add(mapLayer);

    refresh();
  }

  private static void refresh() {
    MapPoint currentCenter = mapView.getCenter();
    double latitude = currentCenter.getLatitude();
    double longitude = currentCenter.getLongitude();

    double dep = 0.0000000000001;
    MapPoint newCenter = new MapPoint(latitude + dep, longitude - dep);

    // Déplacement de la carte vers les nouvelles coordonnées
    mapView.flyTo(0, newCenter, 0.1);
  }

}