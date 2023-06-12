package graphics;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CustomCircleMarkerLayer extends MapLayer {

    private MapPoint mapPoint;
    private Circle circle;

    /**
     * Creates a nex point on the map
     * @param mapPoint the point to add on the map
     */
    public CustomCircleMarkerLayer(MapPoint mapPoint) {
        this.mapPoint = mapPoint;

        /* Cercle rouge de taille 5 */
        this.circle = new Circle(3, Color.RED);

        /* Ajoute le cercle au MapLayer */
        this.getChildren().add(circle);
    }

    /**
     * Refresh points when the map is updated
     */
    @Override
    protected void layoutLayer() {
        /* Conversion du MapPoint vers Point2D */
        Point2D point2d = this.getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());

        /* Déplace le cercle existant selon les coordonnées du point */
        circle.setTranslateX(point2d.getX());
        circle.setTranslateY(point2d.getY());
    }
}
