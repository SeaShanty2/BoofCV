/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.alg.feature.describe;

import gecv.alg.feature.describe.impl.NaiveSurfDescribeOps;
import gecv.struct.convolve.Kernel2D_F64;
import gecv.struct.image.ImageBase;


/**
 * Operations related to computing SURF descriptors.
 *
 * @author Peter Abeles
 */
public class SurfDescribeOps {

	/**
	 * Computes the "gradient" using Haar wavelets.  The region considered has a radius
	 * of ceil(radius*s) pixels.  It is samples every 's' pixels by a Haar wavelet.  The
	 * output of the Haar wavelet is stored om the derivX and derivY variables.
	 *
	 *
	 * @param ii Integral image.
	 * @param c_x Center pixel.
	 * @param c_y Center pixel.
	 * @param radius Radius of region being considered in samples.
	 * @param s Scale of feature.
	 * @param derivX Haar x wavelet output.
	 * @param derivY Haar Y wavelet output.
	 */
	public static <T extends ImageBase>
	void gradient( T ii , int c_x , int c_y ,
				   int radius , double s ,
				   double []derivX , double derivY[] )
	{
		NaiveSurfDescribeOps.gradient(ii,c_x,c_y,radius,s,derivX,derivY);
	}


	/**
	 * <p>
	 * Computes features in the SURF descriptor.
	 * </p>
	 *
	 * <p>
	 * Deviation from paper:<br>
	 * Weighting function is applied to each sub region as a whole and not to each wavelet inside the sub
	 * region.  This allows the weight to be precomputed once.  Unlikely to degrade quality significantly.
	 * </p>
	 *
	 * @param ii Integral image.
	 * @param c_x Center of the feature x-coordinate.
	 * @param c_y Center of the feature y-coordinate.
	 * @param theta Orientation of the features.
	 * @param weight Gaussian normalization.
	 * @param regionSize Number of wavelets wide.
	 * @param numSubRegions How many sub-regions is the large region divided along its width.
	 * @param scale The scale of the wavelets.
	 * @param features Where the features are written to.  Must be 4*numSubRegions^2 large.
	 */
	public static <T extends ImageBase>
	void features( T ii , int c_x , int c_y ,
				   double theta , Kernel2D_F64 weight ,
				   int regionSize , int numSubRegions , double scale ,
				   double []features )
	{
		NaiveSurfDescribeOps.features(ii,c_x,c_y,theta,weight,regionSize,numSubRegions,scale,features);
	}

	// todo move to a generalized class?
	public static void normalizeFeatures( double []features ) {
		double norm = 0;
		for( int i = 0; i < features.length; i++ ) {
			double a = features[i];
			norm += a*a;
		}
		// if the norm is zero, don't normalize
		if( norm == 0 )
			return;
		
		norm = Math.sqrt(norm);
		for( int i = 0; i < features.length; i++ ) {
			features[i] /= norm;
		}
	}
}
