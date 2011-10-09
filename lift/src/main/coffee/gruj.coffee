$ ->
  # -----------------------------------------------------
  # Global coffeescript that gets executed on every page
  # -----------------------------------------------------

  # rel=external links are opened in blank tab
  $('a[rel=external]').attr('target', '_blank')
