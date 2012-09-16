/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.physics.editor.scene.widgets;

import de.eppleton.physics.editor.scene.WorldEditorScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import org.jbox2d.collision.shapes.CircleShape;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.Widget.Dependency;

public class CircleWidget extends Widget implements Dependency{
    
    private int diameter;
    private CircleShape shape;
    private final WorldEditorScene worldScene;
    
    public CircleWidget(WorldEditorScene scene, int radius, CircleShape shape) {
        super(scene);
        this.worldScene = scene;
        this.shape = shape;
        setRadius(radius);
        addDependency(this);
    }
    
    @Override
    protected Rectangle calculateClientArea() {
        int r = diameter / 2;
        return new Rectangle(-r,-r, diameter + 1, diameter + 1);
    }
    
    @Override
    protected void paintWidget() {
        Rectangle bounds = getBounds();
        int x = bounds.x + getBorder().getInsets().left;
        int y = bounds.y + getBorder().getInsets().top;
        int width = bounds.width - getBorder().getInsets().left - getBorder().getInsets().right - 1;
        int height = bounds.height - getBorder().getInsets().top - getBorder().getInsets().bottom - 1;
        diameter = height > width ? width : height;
        Graphics2D g = getGraphics();
        shape.m_radius = ((float)diameter/2f)/ worldScene.getScale();
        Paint paint = g.getPaint(); 
        Color fg = getForeground();
        Color c = new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 100);
        g.setPaint(c);
        g.fillOval(x, y, diameter, diameter);
        g.setColor(fg);
        g.drawOval(x, y, diameter, diameter);
        g.setPaint(paint);
    }
    
    public void setRadius(int r) {
        this.diameter = r * 2;
        revalidate();
    }
    
    public int getRadius() {
        return diameter / 2;
    }

    
    
    
    
    @Override
    public void revalidateDependency() {
        
    }
    
   
}
