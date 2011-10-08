$ ->
  $('.tabbar .column a').click (e) ->
    e.preventDefault()
    tab = $(this).attr('class')
    $('.post.tab').hide()
    $('.post.tab.' + tab).show()
    $('.tabbar .column').removeClass 'active'
    $(this).parent().addClass 'active'
