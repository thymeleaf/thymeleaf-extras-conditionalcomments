
Thymeleaf - Module for Internet Explorer conditional comments
=============================================================

------------------------------------------------------------------------------

Status
------

This is a *thymeleaf extras* module, not a part of the Thymeleaf core (and as
such following its own versioning schema), but fully supported by the 
Thymeleaf team.

Current version: **1.0.0-beta1**


License
-------

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).


Requirements
------------

  *   Thymeleaf **2.0.15+**
  *   Attoparser **1.1+** [http://www.attoparser.org]


Maven info
----------

  *   groupId: `org.thymeleaf.extras`   
  *   artifactId: `thymeleaf-extras-conditionalcomments`


Installation
------------

Just add the `org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect`
class to the list of dialects in your TemplateEngine implementation, and conditional comment
processing will work out-of-the-box.


Features
--------

This module allows you to correctly process Internet Explorer Conditional
comments inside Thymeleaf templates.
