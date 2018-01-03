#' @export
word_cloud <- function(text, width=600L, height=600L, padding=2L,
                       palette_size=10L, min_font_size=8L, max_font_size=16L,
                       scale=c("log", "sqrt", "linear")) {

  scale <- match.arg(tolower(trimws(scale)), c("log", "sqrt", "linear"))

  cwc <- J("is.rud.kumo.App")$word_cloud

  freq_file <- tempfile(fileext=".txt")
  out_file <- tempfile(fileext=".png")

  on.exit(unlink(freq_file), add=TRUE)
  on.exit(unlink(out_file), add=TRUE)

  cat(text, file=freq_file, sep="\n")

  cwc(freq_file, out_file,
      as.integer(width), as.integer(height),
      as.integer(padding), as.integer(palette_size),
      as.integer(min_font_size), as.integer(max_font_size),
      scale)

  img <- magick::image_read(out_file)

  return(img)

}
