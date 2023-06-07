package graphics;


import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;


import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GluonMapExample {

  public static MapView mapView;
  private static List<MapLayer> mapLayers = new ArrayList<>();

  public static VBox displayMap() {
    Stage stage = new Stage();
    VBox root = new VBox();
    mapView = new MapView();

    System.setProperty("javafx.platform", "desktop");
    System.setProperty("http.agent", "Gluon Mobile/1.0.3");

    MapPoint mapPoint = new MapPoint(46.227638, 2.213749);
    mapView.setZoom(5);

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


  private static void refresh(){
    MapPoint currentCenter = mapView.getCenter();
    double latitude = currentCenter.getLatitude();
    double longitude = currentCenter.getLongitude();

    double dep = 0.0000000000001;
    MapPoint newCenter = new MapPoint(latitude + dep, longitude - dep);

    // Déplacement de la carte vers les nouvelles coordonnées
    mapView.flyTo(0, newCenter, 0.1);
  }



}

