package generators;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicChartParameters;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class SystemComplexityGenerator extends PolymorphicChartGenerator {

	//TODO which attributes can be placed in superclass?
	private int width;
	private int height;
	private String boxHeight;
	private String boxWidth;
	private String boxColor;
	private List<Shape> shapes;//The collection of shapes, displayed on the view
	private List<BoxTree> dependencyTrees;
	ShapeGenerator boxGenerator;
	//tree
	
	
	/** 
	 * This constructor creates a new systemcomplexitygenerator object. 
	 * @param polyParams specify the chart that should be built
	 * @param sonar is a facade that can be used to access data about the analyzed project
	 */
	public SystemComplexityGenerator(PolymorphicChartParameters polyParams, SonarFacade sonar) {
		super(polyParams,sonar);
		
		this.boxHeight = polyParams.getBoxHeight();
		this.boxWidth = polyParams.getBoxWidth();
		this.boxColor = polyParams.getBoxColor();
		this.dependencyTrees= measureFetcher.getDependencyTrees();
		boxGenerator = new BoxGenerator(measureFetcher,polyParams);
		shapes.addAll(Arrays.asList(boxGenerator.getShapes()));
	}

	
	/**
	 * This method generates the image to be displayed as view
	 * @return the systemcomplexity view as requested as a BuffereredImage
	 */
	@Override
	public BufferedImage generateImage() {

		Map<String,Double> boxHeights = measureFetcher.getMeasureValues(boxHeight);
		Map<String,Double> boxWidths = measureFetcher.getMeasureValues(boxWidth);
	    builder.createCanvas(height, width, BufferedImage.TYPE_INT_RGB);
	    
	    
	    buildShapes(boxHeights, boxWidths);
	    
		return builder.getImage();
	}

	public void buildShapes(Map<String, Double> heights, Map<String, Double> widths){
		//TODO horizontal ordering: alphabetical String.compareTo(otherString)
		//TODO vertical ordering: hierarchy + height= max height of yMetric of that row

		for(Shape shape : this.shapes){

			shape.draw(this.builder);
		}
	}
	
	public void buildLines(BoxTree dependencyTree){
		for(Shape shape:this.shapes){
			
		}
	}
}
