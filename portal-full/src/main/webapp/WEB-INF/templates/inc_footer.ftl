<#assign TermsConditionsCurrent = ""/>
<#assign LanguagePolicyCurrent = ""/>
<#assign PrivacyCurrent = ""/>
<#assign AccessibilityCurrent = ""/>
<#assign AboutUsCurrent = ""/>
<#assign ContactCurrent = ""/>
<#assign UsingEuropeanaCurrent = ""/>
<#assign SiteMapCurrent = ""/>
<#if thisPage?exists>
    <#switch thisPage>
        <#case "termsofservice.html">
        <#assign TermsConditionsCurrent = "current"/>
            <#break>
        <#case "languagepolicy.html">
        <#assign LanguagePolicyCurrent = "current"/>
            <#break>
        <#case "privacy.html">
        <#assign PrivacyCurrent = "current"/>
            <#break>
        <#case "accessibility.html">
            <#assign AccessibilityCurrent = "current"/>
        <#break>
        <#case "aboutus.html">
            <#assign AboutUsCurrent = "current"/>
        <#break>
        <#case "contact.html">
            <#assign ContactCurrent = "current"/>
        <#break>
        <#case "usingeuropeana.html">
            <#assign UsingEuropeanaCurrent = "current"/>
        <#break>
        <#case "sitemap.html">
            <#assign SiteMapCurrent = "current"/>
        <#break>
        <#default>
            <#break>
    </#switch>
</#if>
<ul>
  <li><a href="aboutus.html" class="${AboutUsCurrent}"><@spring.message 'AboutUs_t' /></a></li>
  <li><a href="using-europeana.html" class="${UsingEuropeanaCurrent}"><@spring.message 'UsingEuropeana_t' /></a></li>
  <li><a href="accessibility.html" class="${AccessibilityCurrent}"><@spring.message 'Accessibility_t' /></a></li>
  <li><a href="sitemap.html"  class="${SiteMapCurrent}"><@spring.message 'Sitemap_t' /></a></li>
  <li><a href="termsofservice.html" class="${TermsConditionsCurrent}"><@spring.message 'TermsAndConditions_t' /></a></li>
  <li><a href="privacy.html" class="${PrivacyCurrent}"><@spring.message 'Privacy_t' /></a></li>
  <li><a href="languagepolicy.html" class="${LanguagePolicyCurrent}"><@spring.message 'LanguagePolicy_t' /></a></li>
  <li><a href="contact.html" class="${ContactCurrent}"><span  class="fg-red"><@spring.message 'Contacts_t' /> | <@spring.message 'SendUsFeedback_t' /></span></a></li>
  <li class="signoff"><@spring.message 'FundedBy_t' /><img src="images/eu-flag.gif" alt="icon-european union flag"/></li>
</ul>

<#-- Piwik tracking -->
<#if piwik_js?? >
    <script type="text/javascript" src="${piwik_js}"></script>
    <script type="text/javascript">
        var piwik_action_name = '';
        var piwik_idsite = 1;
        var piwik_url = "${piwik_log_url}";
        piwik_log(piwik_action_name, piwik_idsite, piwik_url);
    </script>
</#if>