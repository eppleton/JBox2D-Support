package de.eppleton.jbox2d;

/*
* Convex Separator for Box2D Flash
*
* This class has been written by Antoan Angelov.
* It is designed to work with Erin Catto's Box2D physics library.
*
* Everybody can use this software for any purpose, under two restrictions:
* 1. You cannot claim that you wrote this software.
* 2. You can not remove or alter this notice.
*
* Postscript:
*   This Java port for JBox2D has been written by Levi Van Zele.
*
*/


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConvexSeparator {

    /**
     * Separates a concave polygon into convex polygons and adds them as fixtures to the <code>body</code> parameter.<br/>
     * There are some rules you should follow (otherwise you might get unexpected results) :
     * <ul>
     * <li>This class is specifically for non-convex polygons. If you want to create a convex polygon, you don't need to use this class - Box2D's <code>b2PolygonShape</code> class allows you to create convex shapes with the <code>setAsArray()</code>/<code>setAsVector()</code> method.</li>
     * <li>The vertices must be in clockwise order.</li>
     * <li>No three neighbouring points should lie on the same line segment.</li>
     * <li>There must be no overlapping segments and no "holes".</li>
     * </ul> <p/>
     *
     * @param body       The {@link Body}, in which the new fixtures will be stored.
     * @param fixtureDef A {@link FixtureDef}, containing all the properties (friction, density, etc.) which the new fixtures will inherit.
     * @param vertices   The vertices of the non-convex polygon, in clockwise order.
     */
    public void separate(Body body, FixtureDef fixtureDef, List<Vec2> vertices) {
        separate(body, fixtureDef, vertices, 30);
    }

    /**
     * Separates a concave polygon into convex polygons and adds them as fixtures to the <code>body</code> parameter.<br/>
     * There are some rules you should follow (otherwise you might get unexpected results) :
     * <ul>
     * <li>This class is specifically for non-convex polygons. If you want to create a convex polygon, you don't need to use this class - Box2D's <code>b2PolygonShape</code> class allows you to create convex shapes with the <code>setAsArray()</code>/<code>setAsVector()</code> method.</li>
     * <li>The vertices must be in clockwise order.</li>
     * <li>No three neighbouring points should lie on the same line segment.</li>
     * <li>There must be no overlapping segments and no "holes".</li>
     * </ul> <p/>
     *
     * @param body       The {@link Body}, in which the new fixtures will be stored.
     * @param fixtureDef A {@link FixtureDef}, containing all the properties (friction, density, etc.) which the new fixtures will inherit.
     * @param vertices   The vertices of the non-convex polygon, in clockwise order.
     * @param scale      The scale which you use to draw shapes in Box2D. The bigger the scale, the better the precision.
     */
     public void separate(Body body, FixtureDef fixtureDef, List<Vec2> vertices, int scale) {
         List<Vec2> scaledVertices = new ArrayList<Vec2>();
         for (Vec2 vec2 : vertices) {
             scaledVertices.add(new Vec2(vec2.x * scale, vec2.y * scale));
         }
 
         List<List<Vec2>> shapeList = calcShapes(scaledVertices);
         for (List<Vec2> shape : shapeList) {
             Vec2[] normalizedVertices = new Vec2[shape.size()];
             for (int i = 0; i < shape.size(); i++) {
                 Vec2 scaled = shape.get(i);
                 normalizedVertices[i] = new Vec2(scaled.x / scale, scaled.y / scale);
             }
             normalizedVertices = removeZeroLengthEdges(normalizedVertices);
 
             if (normalizedVertices.length > Settings.maxPolygonVertices) {
                 Settings.maxPolygonVertices = normalizedVertices.length;
             }
 
             PolygonShape polyShape = new PolygonShape();
             polyShape.set(normalizedVertices, normalizedVertices.length);
             fixtureDef.shape = polyShape;
             body.createFixture(fixtureDef);
         }
     }

     private Vec2[] removeZeroLengthEdges(Vec2[] normalizedVertices) {
         if (normalizedVertices.length > 1) {
             Vec2 first = normalizedVertices[0];
             if (first.equals(normalizedVertices[normalizedVertices.length - 1])) {
                 Deque<Vec2> nonZeroVertices = new LinkedList<Vec2>(Arrays.asList(normalizedVertices));
                 nonZeroVertices.removeLast();
                 return nonZeroVertices.toArray(new Vec2[nonZeroVertices.size()]);
             }
         }
         return normalizedVertices;
     }

    /**
     * Checks whether the vertices in <code>verticesVec</code> can be properly distributed into the new fixtures (more specifically, it makes sure there are no overlapping segments and the vertices are in clockwise order).
     * It is recommended that you use this method for debugging only, because it may cost more CPU usage.
     * <p/>
     *
     * @param vertices The vertices to be validated.
     * @return An integer which can have the following values:
     *         <ul>
     *         <li>0 if the vertices can be properly processed.</li>
     *         <li>1 If there are overlapping lines.</li>
     *         <li>2 if the points are <b>not</b> in clockwise order.</li>
     *         <li>3 if there are overlapping lines <b>and</b> the points are <b>not</b> in clockwise order.</li>
     *         </ul>
     */
    public int validate(List<Vec2> vertices) {
        int listSize = vertices.size();
        int ret = 0;

        boolean fl2 = false;
        for (int i = 0; i < listSize; i++) {
            int i2 = (i < listSize - 1) ? i + 1 : 0;
            int i3 = (i > 0) ? i - 1 : listSize - 1;

            boolean fl = false;
            for (int j = 0; j < listSize; j++) {
                if (j != i && j != i2) {
                    if (!fl) {
                        float d = det(vertices.get(i).x, vertices.get(i).y,
                                vertices.get(i2).x, vertices.get(i2).y,
                                vertices.get(j).x, vertices.get(j).y);
                        if (d > 0) {
                            fl = true;
                        }
                    }

                    if ((j != i3)) {
                        int j2 = j < listSize - 1 ? j + 1 : 0;
                        Vec2 hit = hitSegment(vertices.get(i).x, vertices.get(i).y,
                                vertices.get(i2).x, vertices.get(i2).y,
                                vertices.get(j).x, vertices.get(j).y,
                                vertices.get(j2).x, vertices.get(j2).y);
                        if (hit != null) {
                            ret = 1;
                        }
                    }
                }
            }

            if (!fl) {
                fl2 = true;
            }
        }

        if (fl2) {
            if (ret == 1) {
                ret = 3;
            } else {
                ret = 2;
            }

        }
        return ret;
    }

    private List<List<Vec2>> calcShapes(List<Vec2> verticesVec) {
        List<List<Vec2>> separations = new ArrayList<List<Vec2>>();

        Queue<List<Vec2>> queue = new LinkedList<List<Vec2>>();
        queue.add(verticesVec);

        boolean isConvex;
        while (!queue.isEmpty()) {
            List<Vec2> list = queue.peek();
            isConvex = true;

            int listSize = list.size();
            for (int i1 = 0; i1 < listSize; i1++) {
                int i2 = i1 < listSize - 1 ? i1 + 1 : i1 + 1 - listSize;
                int i3 = i1 < listSize - 2 ? i1 + 2 : i1 + 2 - listSize;

                Vec2 p1 = list.get(i1);
                Vec2 p2 = list.get(i2);
                Vec2 p3 = list.get(i3);

                float result = det(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
                if (result < 0) {
                    isConvex = false;
                    float minLen = Float.MAX_VALUE;

                    int j1;
                    int j2;
                    int h = 0;
                    int k = 0;

                    Vec2 v1 = null;
                    Vec2 v2 = null;
                    Vec2 hitV = null;

                    for (j1 = 0; j1 < listSize; j1++) {
                        if (j1 != i1 && j1 != i2) {
                            j2 = j1 < listSize - 1 ? j1 + 1 : 0;

                            v1 = list.get(j1);
                            v2 = list.get(j2);

                            Vec2 v = hitRay(p1.x, p1.y, p2.x, p2.y, v1.x, v1.y, v2.x, v2.y);

                            if (v != null) {
                                float dx = p2.x - v.x;
                                float dy = p2.y - v.y;
                                float t = dx * dx + dy * dy;

                                if (t < minLen) {
                                    h = j1;
                                    k = j2;
                                    hitV = v;
                                    minLen = t;
                                }
                            }
                        }
                    }

                    if (minLen == Float.MAX_VALUE) {
                        err();
                    }

                    List<Vec2> vec1 = new ArrayList<Vec2>();
                    List<Vec2> vec2 = new ArrayList<Vec2>();

                    j1 = h;
                    j2 = k;

                    if (!pointsMatch(hitV.x, hitV.y, v2.x, v2.y)) {
                        vec1.add(hitV);
                    }

                    if (!pointsMatch(hitV.x, hitV.y, v1.x, v1.y)) {
                        vec2.add(hitV);
                    }

                    h = -1;
                    k = i1;

                    while (true) {
                        if (k != j2) {
                            vec1.add(list.get(k));
                        } else {
                            if (h < 0 || h >= listSize) {
                                err();
                            }
                            if (!isOnSegment(v2.x, v2.y, list.get(h).x, list.get(h).y, p1.x, p1.y)) {
                                vec1.add(list.get(k));
                            }
                            break;
                        }

                        h = k;
                        if (k - 1 < 0) {
                            k = listSize - 1;
                        } else {
                            k--;
                        }
                    }

                    Collections.reverse(vec1);

                    h = -1;
                    k = i2;

                    while (true) {
                        if (k != j1) {
                            vec2.add(list.get(k));
                        } else {
                            if (h < 0 || h >= listSize) {
                                err();
                            }
                            if (k == j1 && !this.isOnSegment(v1.x, v1.y, list.get(h).x, list.get(h).y, p2.x, p2.y)) {
                                vec2.add(list.get(k));
                            }
                            break;
                        }

                        h = k;
                        if (k + 1 > listSize - 1) {
                            k = 0;
                        } else {
                            k++;
                        }
                    }

                    queue.add(vec1);
                    queue.add(vec2);
                    queue.poll();
                    break;
                }
            }

            if (isConvex) {
                separations.add(queue.poll());
            }
        }

        return separations;
    }

    private Vec2 hitRay(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float t1 = x3 - x1;
        float t2 = y3 - y1;
        float t3 = x2 - x1;
        float t4 = y2 - y1;
        float t5 = x4 - x3;
        float t6 = y4 - y3;
        float t7 = t4 * t5 - t3 * t6;

        float a = (t5 * t2 - t6 * t1) / t7;
        float px = x1 + a * t3, py = y1 + a * t4;

        boolean b1 = isOnSegment(x2, y2, x1, y1, px, py);
        boolean b2 = isOnSegment(px, py, x3, y3, x4, y4);

        if (b1 && b2) {
            return new Vec2(px, py);
        }
        return null;
    }

    private Vec2 hitSegment(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float t1 = x3 - x1;
        float t2 = y3 - y1;
        float t3 = x2 - x1;
        float t4 = y2 - y1;
        float t5 = x4 - x3;
        float t6 = y4 - y3;
        float t7 = t4 * t5 - t3 * t6;

        float a = (t5 * t2 - t6 * t1) / t7;
        float px = x1 + a * t3, py = y1 + a * t4;

        boolean b1 = isOnSegment(px, py, x1, y1, x2, y2);
        boolean b2 = isOnSegment(px, py, x3, y3, x4, y4);

        if (b1 && b2) {
            return new Vec2(px, py);
        }
        return null;
    }

    private boolean isOnSegment(float px, float py, float x1, float y1, float x2, float y2) {
        boolean b1 = ((x1 + 0.1 >= px && px >= x2 - 0.1) || (x1 - 0.1 <= px && px <= x2 + 0.1));
        boolean b2 = ((y1 + 0.1 >= py && py >= y2 - 0.1) || (y1 - 0.1 <= py && py <= y2 + 0.1));
        return b1 && b2 && isOnLine(px, py, x1, y1, x2, y2);
    }

    private boolean pointsMatch(float x1, float y1, float x2, float y2) {
        float dx = x2 >= x1 ? x2 - x1 : x1 - x2;
        float dy = y2 >= y1 ? y2 - y1 : y1 - y2;
        return dx < 0.1 && dy < 0.1;
    }

    private boolean isOnLine(float px, float py, float x1, float y1, float x2, float y2) {
        if (x2 - x1 > 0.1 || x1 - x2 > 0.1) {
            float a = (y2 - y1) / (x2 - x1);
            float possibleY = a * (px - x1) + y1;
            float diff = possibleY > py ? possibleY - py : py - possibleY;
            return diff < 0.1;
        }
        return px - x1 < 0.1 || x1 - px < 0.1;
    }

    private float det(float x1, float y1, float x2, float y2, float x3, float y3) {
        return x1 * y2 + x2 * y3 + x3 * y1 - y1 * x2 - y2 * x3 - y3 * x1;
    }

    private void err() {
        throw new IllegalArgumentException("A problem has occurred, Use the validate() method to see where the problem is.");
    }

}