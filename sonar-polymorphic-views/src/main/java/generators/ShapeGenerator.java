//package generators;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicChartParameters;
//
//public abstract class ShapeGenerator {
//
//	protected MeasureFetcher measureFetcher;
//	protected List<Shape> shapes;
//
//	public ShapeGenerator(MeasureFetcher measureFetcher) {
//		this.measureFetcher = measureFetcher;
//	}
//
//	/**
//	 * This method generates a list of boxes with size and color defined by the
//	 * params input. Size and/or color may be either a fixed value, or a value
//	 * defined by a given metric.
//	 * 
//	 * @param params
//	 *            defines the sizes and colors of the boxes
//	 * @return an array of boxes with the given dimension/color
//	 */
//	public abstract Shape[] getShapes(String width, String height, String color);
//
//	/**
//	 * This method returns a list with the needed dimensions of the shapes. If
//	 * the input can be parsed to an int, all shapes have the same dimension, if
//	 * the input is a string, the dimension of the boxes is dependent on the
//	 * metric with the input as its key.
//	 * 
//	 * @param dimension
//	 *            either the dimension, or the string defining the metric
//	 *            needed.
//	 * @return a list with the width/height of all the boxes
//	 */
//	protected List<Double> getShapeDimension(String dimension) {
//		List<Double> dimensions;
//		try {
//			Double parsedDimension = Double.parseDouble(dimension);
//			dimensions = new ArrayList<Double>(Collections.nCopies(
//					shapes.size(), parsedDimension));
//		} catch (NumberFormatException e) {
//			Map<String, Double> values = measureFetcher
//					.getMeasureValues(dimension);
//			dimensions = getShapeSize(values);
//		}
//		return dimensions;
//	}
//
//	/**
//	 * This method returns a list of double-typed values for a certain property
//	 * of a set of shapes. The values are in the same order as the shapes in the
//	 * shapes array.
//	 * 
//	 * @param values
//	 */
//	private List<Double> getShapeSize(Map<String, Double> values) {
//		List<Double> result = new ArrayList<Double>();
//		for (int i = 0; i < shapes.size(); i++) {
//			for (String s : values.keySet()) {
//				if (shapes.get(i).getName().equals(s)) {
//					result.add(values.get(s));
//				}
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * TODO aanpa This method returns a list of colors, one for each shape. If
//	 * the input is in RGB format, the color is the same for all shapes. If the
//	 * format "min<float>max<float>key<string>" is used as input, the color of
//	 * each box is dependent on a specific measure of the box. Else, the color
//	 * will be set to default color
//	 * 
//	 * @param color
//	 *            the color/metric to be used
//	 * @return a list with a color for each box
//	 */
//	protected List<Color> getShapeColors(String color) {
//		List<Color> result = new ArrayList<Color>();
//		try {
//			Color rgb = parseColor(color);
//			result = new ArrayList<Color>(Collections.nCopies(shapes.size(),
//					rgb));
//
//		} catch (Exception e) {
//			// TODO mss in aparte methode steken dit parsen? Kan eventueel zelfs
//			// samen met parseColor als ge ne lijst met delimiters geeft?
//			float min = Float.parseFloat(color.split("min")[1].split("max")[0]);
//			float max = Float.parseFloat(color.split("max")[1].split("key")[0]);
//			String key = color.split("key")[1];
//
//			Map<String, Double> colors = measureFetcher.getMeasureValues(key);
//
//			Map<String, Double> scaledColors = ScatterPlotGenerator.scale(
//					colors, min, max);
//			result = new ArrayList<Color>();
//
//			for (int i = 0; i < shapes.size(); i++) {
//				String name = shapes.get(i).getName();
//				int colorValue = scaledColors.get(name).intValue();
//				Color c = new Color(colorValue, colorValue, colorValue);
//				result.add(c);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * This method parses an input string to an rgb color. If the input is not
//	 * of the form rxxxgxxxbxxx, it should be of the form
//	 * min<float>max<float>key<string>. In the first case, the color will be the
//	 * same for all shapes, in the second case, the color will be depending on a
//	 * specific metric.
//	 * 
//	 * @param color
//	 * @return
//	 * @throws IllegalArgumentException
//	 */
//	static Color parseColor(String color) throws IllegalArgumentException {
//		Integer[] result = new Integer[3];
//		try {
//			result[0] = Integer.parseInt(color.split("r")[1].split("g")[0]);
//			result[1] = Integer.parseInt(color.split("g")[1].split("b")[0]);
//			result[2] = Integer.parseInt(color.split("b")[1]);
//		} catch (NumberFormatException e) {
//			throw new IllegalArgumentException();
//		}
//		return new Color(result[0], result[1], result[2]);
//	}
//
//	/**
//	 * This method names all the shapes with the correct resource names.
//	 */
//	protected void nameShapes() {
//		List<String> names = measureFetcher.getResourceNames();
//		int i = 0;
//		for (String s : names) {
//			shapes.get(i).setName(s);
//			i++;
//		}
//	}
//
//}