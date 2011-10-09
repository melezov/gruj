$ ->
  # -----------------------------------------------------
  # /examples coffeescripts
  # -----------------------------------------------------

  # tab click handler
  $('.tabbar li.column').click (e) ->
    e.preventDefault()

    # don't do anything if that tab is already selected
    unless $(@).is '.active'

      # select new tab, deactivate others
      $('.tabbar .column.active').removeClass 'active'
      $(@).addClass 'active'

      # persist last view in Active SessionVar
      url = $(@).find('a').attr 'href'
      url = url.replace('#', '/')
      $.get("#{url}/ping")

      # show new tab content
      id = $(@).attr 'id'
      $('.post.tab').hide()
      $("##{id}-tab").show()
