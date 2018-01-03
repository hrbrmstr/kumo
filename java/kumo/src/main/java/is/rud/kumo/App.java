package is.rud.kumo;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.LogFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kennycason.kumo.LayeredWordCloud;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

public class App {

  private static final Random RANDOM = new Random();

  private static ColorPalette buildRandomColorPalette(final int n) {
   final Color[] colors = new Color[n];
   for (int i = 0; i < colors.length; i++) {
     colors[i] = new Color(RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25, RANDOM.nextInt(230) + 25);
   }
   return new ColorPalette(colors);
  }

  private static InputStream getInputStream(final String path) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
  }

  private static Set<String> loadStopWords(String stop_words_file) throws IOException {
    return new HashSet<>(IOUtils.readLines(getInputStream(stop_words_file)));
  }

  public static void word_cloud(
    String freq_file, String out_file, 
    int width, int height,
    int padding, int palette_size,
    int min_font_size, int max_font_size,
    String scale) throws IOException {

    final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
    frequencyAnalyzer.setWordFrequenciesToReturn(300);
    frequencyAnalyzer.setMinWordLength(4);

    final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(freq_file);

    final Dimension dimension = new Dimension(width, height);
    final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

    wordCloud.setBackground(new RectangleBackground(dimension));
    wordCloud.setKumoFont(new KumoFont("LICENSE PLATE", FontWeight.BOLD));
    wordCloud.setPadding(padding);
    wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
    if (scale == "log") {
      wordCloud.setFontScalar(new LogFontScalar(min_font_size, max_font_size));
    } else if (scale == "sqrt") {
      wordCloud.setFontScalar(new SqrtFontScalar(min_font_size, max_font_size));
    } else {
      wordCloud.setFontScalar(new LinearFontScalar(min_font_size, max_font_size));
    }

    wordCloud.build(wordFrequencies);

    wordCloud.writeToFile(out_file);

  }

}
