<#import "/spring.ftl" as spring />
<#assign thisPage = ""/>
<#include "inc_header.ftl"/>

<div id="doc4" class="yui-t2">
    <div id="hd">
        <#include "inc_top_nav.ftl"/>
    </div>
   <div id="bd">
    <div id="yui-main">
        <div class="yui-b">
            <#if staticPagesSource?matches("database")>
                <#if staticPage?? >${staticPage.content}</#if>
            <#elseif staticPagesSource?matches("include")>
                <@localeBased "include" "inc_usingeuropeana_$$.ftl"/>
            </#if>
        </div>

    </div>
       <div class="yui-b">
            <#include "inc_logo_sidebar.ftl"/>
        </div>
    </div>
   <div id="ft">
	   <#include "inc_footer.ftl"/>
   </div>
</div>
</body>
</html>