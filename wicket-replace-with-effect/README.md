## What is this project useful for?##

It contains a Wicket behavior (ReplaceWithEffectBehavior) that allows to execute some "effects" before and after
a component is replaced via AJAX.

 ## How to use it? ##
 
 Java code
 
```java
 		public HomePage() {
		add(slideDown = new WebMarkupContainer("slideDown"));
		slideDown.add(new ReplaceWithEffectBehavior());
		add(new AjaxLink<Void>("link") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(slideDown);
			}
		});
	}
 ```
 HTML template.
 
 ```html
 <!DOCTYPE html>
<html xmlns:wicket="http://wicket.apache.org">
<head>
<title>Test</title>
<style type="text/css">
	.Test {
		width: 200px;
		height: 200px;
		background: red;
		border: 1px solid black;
	}
</style>
</head>
<body>
	<div wicket:id="slideDown" class="Test">
		Test
	</div>
	<p>
		<a wicket:id="link">Repaint via AJAX</a>
	</p>
</body>
</html>
 ```