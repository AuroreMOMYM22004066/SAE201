package graphics;


import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import datahandling.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GluonMapExample {

  public static MapView mapView;
  private static List<MapLayer> mapLayers = new ArrayList<>();

  public static VBox displayMap() {
    Stage stage = new Stage();
    VBox root = new VBox();
    mapView = new MapView();

    /* Set properties */
    System.setProperty("javafx.platform", "desktop");
    System.setProperty("http.agent", "Gluon Mobile/1.0.3");

    /* Set zoom */
    mapView.setZoom(5.8);

    /* resize the map (fxml) */
    mapView.setMinSize(700, 700);
    mapView.setMaxSize(700, 700);

    /* add the map to the scene */
    root.getChildren().add(mapView);

    /* return the new scene */
    return root;
  }

  public static void addMarker(List<Map<String, String>> data) {

    for (Map<String, String> line: data ) {
      if (line.get(Builder.Header.Latitude_WGS84.getValue()) != "" && line.get(Builder.Header.Longitude_WGS84.getValue()) != "")
      {
        double latitude  = Double.parseDouble(line.get(Builder.Header.Latitude_WGS84.getValue()));
        double longitude = Double.parseDouble(line.get(Builder.Header.Longitude_WGS84.getValue()));



        MapPoint mapPoint = new MapPoint(latitude, longitude);
        MapLayer mapLayer = new CustomCircleMarkerLayer(mapPoint);
        mapView.addLayer(mapLayer);
        mapLayers.add(mapLayer);
      }
    }
    refresh();
  }

  private static void refresh(){
    MapPoint currentCenter = mapView.getCenter();
    double latitude = currentCenter.getLatitude();
    double longitude = currentCenter.getLongitude();

    double dep = 0.0000000000001;
    MapPoint newCenter = new MapPoint(latitude + dep, longitude + dep);

    // Déplacement de la carte vers les nouvelles coordonnées
    mapView.flyTo(0, newCenter, 0.1);
  }

  static void removeMarkers(){
    for (MapLayer mapLayer: mapLayers ) {
      mapView.removeLayer(mapLayer);
    }
  }
}

