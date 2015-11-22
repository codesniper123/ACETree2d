/*
 *  tbd:
 *  
 *  1. X Axis getting truncated.
 *  2. Y Axis needs a margin on the top.
 *  3. Use a small circle instead of the line for the points.
 * 
 */

package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;

public class Drawing {
	
	static final int xscale = 2;
	static final int yscale = 2;
	static final int ylimit = 100 * yscale;
	static final int xlimit = 100 * xscale;
	static final int xmargin = 10 * xscale;
	
	class ScaledPoint {
		protected int x, y;
		protected int actualX, actualY;
		
		private void init(int x, int y) {
			this.actualX = x;
			this.actualY = y;
			this.x = x * xscale + xmargin; 
			this.y = ylimit - y * yscale;			
		}
		
		public ScaledPoint(int x, int y) {
			this.init(x,y);
		}
		
		public ScaledPoint(Point p) {
			this.init(p.x,p.y);
		}
		
	}
	
	protected SVGGraphics2D graphics;
	
	public Drawing(SVGGraphics2D graphics) {
		this.graphics = graphics;
		Font font = new Font("System", Font.PLAIN, 4);
		graphics.setFont(font);
	}
	
	public void drawAxes() {
        ScaledPoint ptOrigin = this.new ScaledPoint(0, 0);
        ScaledPoint ptXAxis = this.new ScaledPoint(xlimit,0);
        ScaledPoint ptYAxis = this.new ScaledPoint(0, ylimit);
        
        graphics.draw(new Line2D.Double(ptOrigin.x, ptOrigin.y, ptXAxis.x, ptXAxis.y ));
        graphics.draw(new Line2D.Double(ptOrigin.x, ptOrigin.y, ptYAxis.x, ptYAxis.y ));
		
		String s;
        
        // let us draw the coordinates for the axes:
        for( int x = 0; x < xlimit; x += 10 ) {
        	ScaledPoint pt1 = this.new ScaledPoint(x, -2);
        	ScaledPoint pt2 = this.new ScaledPoint(x, +2);
        	graphics.draw(new Line2D.Double(pt1.x, pt1.y, pt2.x, pt2.y));
        	if( x != 0 ) {
        		s = String.format( "%d", x);
        		graphics.drawString(s, pt2.x-3, pt2.y + 10 );
        	}
        }
        
     
        for( int y = 0; y < ylimit; y += 10 ) {
        	ScaledPoint pt1 = this.new ScaledPoint(-2, y);
        	ScaledPoint pt2 = this.new ScaledPoint(+2, y);
        	graphics.draw(new Line2D.Double(pt1.x, pt1.y, pt2.x, pt2.y));
        	
        	if( y != 0 ) {
        		s = String.format( "%d", y);
        		graphics.drawString(s, pt2.x-xmargin, pt2.y + 2 );
        	}
        }		
	}
	
	public void drawPoints(ArrayList<Point> points) {
        // Create a new PDF document with a width of 210 and a height of 297
        // SVGGraphics2D g = new SVGGraphics2D(0.0, 0.0, 400, 400);

        // Draw a red ellipse at the position (20, 30) with a width of 100 and a height of 150
        // graphics.setColor(Color.RED);
        // graphics.fillOval(20, 30, 100, 150);
        
        for( Point p : points ) {
	        /* draw a few ScaledPoints */
        	ScaledPoint sp = new ScaledPoint(p);

        	/* kludge - we are drawing a line for a ScaledPoint for now */
	        graphics.draw(new Line2D.Double(sp.x, sp.y, sp.x+1, sp.y+1 ));
	        /* draw the coordinates as well */
	        String s = String.format("%d,%d",  sp.actualX, sp.actualY );
			graphics.drawString(s, sp.x + 3, sp.y + 2 );
	        
        }
	}
	
	public void save(String filename) {
		FileOutputStream file = null;		
        try {
            // Write the PDF output to a file
            file = new FileOutputStream(filename);
            file.write(this.graphics.getBytes());
            file.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
    public static void main(String[] args) throws IOException {
        SVGGraphics2D g = new SVGGraphics2D(0.0, 0.0, 400, 400);
        
        ArrayList<Point> points = new ArrayList<Point>();
        points.add( new Point(25,50) );
        points.add( new Point(52,25) );
        
        Drawing d = new Drawing(g);
        d.drawAxes();
        d.drawPoints(points);
        
        d.save("test1.svg");
    }
}


