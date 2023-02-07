<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="ISO-8859-1" isELIgnored="false"%>

<script>
	$(document).ready(function() {
		<%
		String currentLang = (String) request.getSession().getAttribute("LANG");
		if (currentLang == null) currentLang = "eng";
		%>
		window.CURRENT_LANG = "<%= currentLang %>";

		function changeLang() {
			if (window.CURRENT_LANG == "eng") window.CURRENT_LANG = "rus";
			else if (window.CURRENT_LANG == "rus") window.CURRENT_LANG = "eng";

			if ($('.lang-btn').text() == "Eng") $('.lang-btn').text("Rus");
			else $('.lang-btn').text("Eng");
			$('.eng-lang').toggle();
			$('.rus-lang').toggle();

			$.post("/frontController", {
						"command": "ChangeLang",
						"lang": window.CURRENT_LANG
					});
		}

		if (window.CURRENT_LANG == "rus") {
			$('.lang-btn').text("Rus");
			$('.eng-lang').toggle();
		} else $('.rus-lang').toggle();

		$('#lang-btn').on('click', function() {
			changeLang();
		});
	});
</script>

<script >
	$(function() {
		// Sidebar toggle behavior
		$('#sidebarCollapse').on('click', function() {
			$('#sidebar, #content').toggleClass('active');
		});
	});
</script>

<script>
	$("[sidebarlink]").each(function() {
		this.onclick = function() {

			$("[sidebarlink]").each(function() {
				this.classList.remove("active-sidebar-link");
			});
			$('a[sidebarlink="' + this.getAttribute('sidebarlink') + '"]').each(function() {
				this.classList.add("active-sidebar-link");
			});

			$(".action").each(function() {
				if (this.classList.contains("active")) this.classList.remove("active");
			});
			$("div").find("[id='" + this.getAttribute('sidebarlink') + "']").addClass("active");

			window.ACTIVE_TAB = this.getAttribute('sidebarlink');

			$.post("/frontController", {
						"command": "SetActiveTab",
						"tab": window.ACTIVE_TAB
					})
	}});
</script>

<script>
	<% String activeTab = (String) request.getSession().getAttribute("ACTIVE_TAB"); %>
	window.ACTIVE_TAB = "<%= activeTab %>";
    if (window.ACTIVE_TAB != null) {
        $('a[sidebarlink="' + window.ACTIVE_TAB + '"]').each(function() {
            this.classList.add("active-sidebar-link");
            $("div").find("[id='" + this.getAttribute('sidebarlink') + "']").addClass("active");
        });
    }
</script>