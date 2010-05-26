/*  Copyright (c) 2006-2007, Vladimir Nikic
    All rights reserved.

    Redistribution and use of this software in source and binary forms,
    with or without modification, are permitted provided that the following
    conditions are met:

    * Redistributions of source code must retain the above
      copyright notice, this list of conditions and the
      following disclaimer.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the
      following disclaimer in the documentation and/or other
      materials provided with the distribution.

    * The name of HtmlCleaner may not be used to endorse or promote
      products derived from this software without specific prior
      written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.

    You can contact Vladimir Nikic by sending e-mail to
    nikic_vladimir@yahoo.com. Please include the word "HtmlCleaner" in the
    subject line.
*/

package org.htmlcleaner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.htmlcleaner.audit.ErrorType;
import org.htmlcleaner.audit.HtmlModificationListener;

/**
 * Properties defining cleaner's behaviour
 *
 * Created by: Vladimir Nikic<br/>
 * Date: March, 2008.
 */
public class CleanerProperties implements HtmlModificationListener{

//    public static final String DEFAULT_CHARSET = System.getProperty("file.encoding");
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String BOOL_ATT_SELF = "self";
    public static final String BOOL_ATT_EMPTY = "empty";
    public static final String BOOL_ATT_TRUE = "true";

    private ITagInfoProvider tagInfoProvider;
    /**
     * If this parameter is set to true, ampersand sign (&) that proceeds valid XML character sequences (&XXX;) will not be escaped with &amp;XXX; 
     */
    private boolean advancedXmlEscape;
    private boolean useCdataForScriptAndStyle;
    private boolean translateSpecialEntities;
    private boolean recognizeUnicodeChars;
    private boolean omitUnknownTags;
    private boolean treatUnknownTagsAsContent;
    private boolean omitDeprecatedTags;
    private boolean omitComments;
    private boolean treatDeprecatedTagsAsContent;
    private OptionalOutput omitXmlDeclaration;
    private OptionalOutput omitDoctypeDeclaration;
    private OptionalOutput omitHtmlEnvelope;
    private boolean useEmptyElementTags;
    private boolean allowMultiWordAttributes;
    private String booleanAttributeValues;
    private boolean ignoreQuestAndExclam;
    private boolean allowHtmlInsideAttributes;
    private boolean namespacesAware;
    /**
     * "cause the cleaner cannot keep track of whitespace at that level",
     * there are 2 lists built: one for the head , one for the body. So whitespace that falls outside of the head and body is not preserved
     * this creates at least a newline break.
     * 
     * More work than really wanted at this point to "preserve" the whitespace.
     */
    private boolean addNewlineToHeadAndBody;
    /**
     * Tries to keep inside head all whitespace and comments that were originally there
     */
    private boolean keepWhitespaceAndCommentsInHead;    
    private String hyphenReplacementInComment;
    // comma separate list of tags pruned.
    private String pruneTags;
    // comma separate list of tags allowed.
    private String allowTags;

    private CleanerTransformations cleanerTransformations = new CleanerTransformations();
    
    private List < HtmlModificationListener > htmlModificationListeners;
    
    /**
     * blacklist of tags
     */
    private Set<ITagNodeCondition> pruneTagSet = new HashSet<ITagNodeCondition>();
    /**
     * the list of allowed tags (whitelist approach v. blacklist approach of pruneTags )
     */
    private Set<ITagNodeCondition> allowTagSet = new HashSet<ITagNodeCondition>();
    private String charset;

    public CleanerProperties() {
        reset();
    }
    
    /**
     * @param tagInfoProvider
     */
    public CleanerProperties(ITagInfoProvider tagInfoProvider) {
        reset();
        this.tagInfoProvider = tagInfoProvider;
    }

    /**
     * @param tagInfoProvider the tagInfoProvider to set
     */
    void setTagInfoProvider(ITagInfoProvider tagInfoProvider) {
        this.tagInfoProvider = tagInfoProvider;
    }

    public ITagInfoProvider getTagInfoProvider() {
        return tagInfoProvider;
    }

    public boolean isAdvancedXmlEscape() {
        return advancedXmlEscape;
    }

    public void setAdvancedXmlEscape(boolean advancedXmlEscape) {
        this.advancedXmlEscape = advancedXmlEscape;
    }

    public boolean isUseCdataForScriptAndStyle() {
        return useCdataForScriptAndStyle;
    }

    public void setUseCdataForScriptAndStyle(boolean useCdataForScriptAndStyle) {
        this.useCdataForScriptAndStyle = useCdataForScriptAndStyle;
    }

    public boolean isTranslateSpecialEntities() {
        return translateSpecialEntities;
    }

    /**
     * TODO : use {@link OptionalOutput}
     * @param translateSpecialEntities
     */
    public void setTranslateSpecialEntities(boolean translateSpecialEntities) {
        this.translateSpecialEntities = translateSpecialEntities;
    }

    public boolean isRecognizeUnicodeChars() {
        return recognizeUnicodeChars;
    }

    public void setRecognizeUnicodeChars(boolean recognizeUnicodeChars) {
        this.recognizeUnicodeChars = recognizeUnicodeChars;
    }

    public boolean isOmitUnknownTags() {
        return omitUnknownTags;
    }

    public void setOmitUnknownTags(boolean omitUnknownTags) {
        this.omitUnknownTags = omitUnknownTags;
    }

    public boolean isTreatUnknownTagsAsContent() {
        return treatUnknownTagsAsContent;
    }

    public void setTreatUnknownTagsAsContent(boolean treatUnknownTagsAsContent) {
        this.treatUnknownTagsAsContent = treatUnknownTagsAsContent;
    }

    public boolean isOmitDeprecatedTags() {
        return omitDeprecatedTags;
    }

    public void setOmitDeprecatedTags(boolean omitDeprecatedTags) {
        this.omitDeprecatedTags = omitDeprecatedTags;
    }

    public boolean isTreatDeprecatedTagsAsContent() {
        return treatDeprecatedTagsAsContent;
    }

    public void setTreatDeprecatedTagsAsContent(boolean treatDeprecatedTagsAsContent) {
        this.treatDeprecatedTagsAsContent = treatDeprecatedTagsAsContent;
    }

    public boolean isOmitComments() {
        return omitComments;
    }

    public void setOmitComments(boolean omitComments) {
        this.omitComments = omitComments;
    }

    public boolean isOmitXmlDeclaration() {
        return omitXmlDeclaration == OptionalOutput.omit;
    }

    public void setOmitXmlDeclaration(boolean omitXmlDeclaration) {
        this.omitXmlDeclaration = omitXmlDeclaration?OptionalOutput.omit:OptionalOutput.alwaysOutput;
    }

    /**
     * 
     * @return also return true if omitting the Html Envelope
     */
    public boolean isOmitDoctypeDeclaration() {
        return omitDoctypeDeclaration == OptionalOutput.omit || isOmitHtmlEnvelope();
    }

    public void setOmitDoctypeDeclaration(boolean omitDoctypeDeclaration) {
        this.omitDoctypeDeclaration = omitDoctypeDeclaration?OptionalOutput.omit:OptionalOutput.alwaysOutput;
    }

    public boolean isOmitHtmlEnvelope() {
        return omitHtmlEnvelope == OptionalOutput.omit;
    }

    public void setOmitHtmlEnvelope(boolean omitHtmlEnvelope) {
        this.omitHtmlEnvelope = omitHtmlEnvelope?OptionalOutput.omit:OptionalOutput.alwaysOutput;
    }

    public boolean isUseEmptyElementTags() {
        return useEmptyElementTags;
    }

    public void setUseEmptyElementTags(boolean useEmptyElementTags) {
        this.useEmptyElementTags = useEmptyElementTags;
    }

    public boolean isAllowMultiWordAttributes() {
        return allowMultiWordAttributes;
    }

    public void setAllowMultiWordAttributes(boolean allowMultiWordAttributes) {
        this.allowMultiWordAttributes = allowMultiWordAttributes;
    }

    public boolean isAllowHtmlInsideAttributes() {
        return allowHtmlInsideAttributes;
    }

    public void setAllowHtmlInsideAttributes(boolean allowHtmlInsideAttributes) {
        this.allowHtmlInsideAttributes = allowHtmlInsideAttributes;
    }

    public boolean isIgnoreQuestAndExclam() {
        return ignoreQuestAndExclam;
    }

    public void setIgnoreQuestAndExclam(boolean ignoreQuestAndExclam) {
        this.ignoreQuestAndExclam = ignoreQuestAndExclam;
    }

    public boolean isNamespacesAware() {
        return namespacesAware;
    }

    public void setNamespacesAware(boolean namespacesAware) {
        this.namespacesAware = namespacesAware;
    }    

    public boolean isAddNewlineToHeadAndBody() {
        return addNewlineToHeadAndBody;
    }

    public void setAddNewlineToHeadAndBody(boolean addNewlineToHeadAndBody) {
        this.addNewlineToHeadAndBody = addNewlineToHeadAndBody;
    }       

    public boolean isKeepWhitespaceAndCommentsInHead() {
        return keepWhitespaceAndCommentsInHead;
    }

    public void setKeepWhitespaceAndCommentsInHead(boolean keepHeadWhitespace) {
        this.keepWhitespaceAndCommentsInHead = keepHeadWhitespace;
    }     

    public String getHyphenReplacementInComment() {
        return hyphenReplacementInComment;
    }

    public void setHyphenReplacementInComment(String hyphenReplacementInComment) {
        this.hyphenReplacementInComment = hyphenReplacementInComment;
    }

    public String getPruneTags() {
        return pruneTags;
    }

    /**
     * Resets prune tags set and adds tag name conditions to it. 
     * All the tags listed by pruneTags param are added.
     * 
     * @param pruneTags
     */
    public void setPruneTags(String pruneTags) {
        this.pruneTags = pruneTags;
        this.resetPruneTagSet();
        this.addTagNameConditions(this.pruneTagSet, pruneTags);
    }
    
    /**
     * Adds the condition to existing prune tag set.
     * 
     * @param condition
     */
    public void addPruneTagNodeCondition(ITagNodeCondition condition){
        pruneTagSet.add(condition);
    }    
    
    public Set<ITagNodeCondition> getPruneTagSet() {
        return pruneTagSet;
    }
    
    public String getAllowTags() {
        return allowTags;
    }

    public void setAllowTags(String allowTags) {
        this.allowTags = allowTags;
        this.setAllowTagSet(allowTags);
    }

    private void setAllowTagSet(String allowTags) {
        allowTagSet.clear();
        addTagNameConditions(allowTagSet, allowTags);
    }

    /**
     * @param tagSet 
     * @param tagsNameStr
     */
    private void addTagNameConditions(Set<ITagNodeCondition> tagSet, String tagsNameStr) {
        if (tagsNameStr != null) {
            StringTokenizer tokenizer = new StringTokenizer(tagsNameStr, ",");
            while ( tokenizer.hasMoreTokens() ) {
                tagSet.add( new TagNode.TagNodeNameCondition(tokenizer.nextToken().trim().toLowerCase()) );
            }
        }
    }
    
    public Set<ITagNodeCondition> getAllowTagSet() {
        return allowTagSet;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    public String getBooleanAttributeValues() {
        return booleanAttributeValues;
    }

    public void setBooleanAttributeValues(String booleanAttributeValues) {
        if ( BOOL_ATT_SELF.equalsIgnoreCase(booleanAttributeValues) ||
             BOOL_ATT_EMPTY.equalsIgnoreCase(booleanAttributeValues) ||
             BOOL_ATT_TRUE.equalsIgnoreCase(booleanAttributeValues) ) {
            this.booleanAttributeValues = booleanAttributeValues.toLowerCase();
        } else {
            this.booleanAttributeValues = BOOL_ATT_SELF;
        }
    }

    /**
     * advancedXmlEscape = true;
     * useCdataForScriptAndStyle = true;
     * translateSpecialEntities = true;
     * recognizeUnicodeChars = true; 
     * omitUnknownTags = false; 
     * treatUnknownTagsAsContent = false;
     * omitDeprecatedTags = false;
     * treatDeprecatedTagsAsContent = false; 
     * omitComments = false;
     * omitXmlDeclaration = OptionalOutput.alwaysOutput; 
     * omitDoctypeDeclaration = OptionalOutput.alwaysOutput; 
     * omitHtmlEnvelope = OptionalOutput.alwaysOutput;
     * useEmptyElementTags = true; 
     * allowMultiWordAttributes = true; 
     * allowHtmlInsideAttributes = false; 
     * ignoreQuestAndExclam = false; 
     * namespacesAware = true;
     * keepHeadWhitespace = true;
     * addNewlineToHeadAndBody = true; 
     * hyphenReplacementInComment = "="; 
     * pruneTags = null; 
     * allowTags = null;
     * booleanAttributeValues = BOOL_ATT_SELF; 
     * collapseNullHtml = CollapseHtml.none
     * charset = "UTF-8";
     */
    public void reset() {
        advancedXmlEscape = true;
        useCdataForScriptAndStyle = true;
        translateSpecialEntities = true;
        recognizeUnicodeChars = true;
        omitUnknownTags = false;
        treatUnknownTagsAsContent = false;
        omitDeprecatedTags = false;
        treatDeprecatedTagsAsContent = false;
        omitComments = false;
        omitXmlDeclaration = OptionalOutput.alwaysOutput;
        omitDoctypeDeclaration = OptionalOutput.alwaysOutput;
        omitHtmlEnvelope = OptionalOutput.alwaysOutput;
        useEmptyElementTags = true;
        allowMultiWordAttributes = true;
        allowHtmlInsideAttributes = false;
        ignoreQuestAndExclam = false;
        namespacesAware = true;
        addNewlineToHeadAndBody = true;
        keepWhitespaceAndCommentsInHead = true;
        hyphenReplacementInComment = "=";
        setPruneTags(null);
        setAllowTags(null);
        booleanAttributeValues = BOOL_ATT_SELF;
        charset = "UTF-8";
        cleanerTransformations.clear();
        resetPruneTagSet();
        tagInfoProvider = DefaultTagProvider.INSTANCE;
        htmlModificationListeners = new ArrayList < HtmlModificationListener >();
    }

    private void resetPruneTagSet() {
        pruneTagSet.clear();
        pruneTagSet.add(TagNodeAutoGeneratedCondition.INSTANCE);
    }

    /**
     * @return the cleanerTransformations
     */
    public CleanerTransformations getCleanerTransformations() {
        return cleanerTransformations;
    }
    
    public void setCleanerTransformations(CleanerTransformations cleanerTransformations) {
        if ( cleanerTransformations == null ) {
            this.cleanerTransformations.clear();
        } else {
            this.cleanerTransformations = cleanerTransformations;
        }
    }
    
    /**
     * Adds a listener to the list of objects that will be notified about changes that 
     * cleaner does during cleanup process.
     * 
     * @param listener -- listener object to be notified of the changes.
     */
    public void addHtmlModificationListener(HtmlModificationListener listener){
        htmlModificationListeners.add(listener);
    }

    @Override
    public void fireConditionModification(ITagNodeCondition condition, TagNode tagNode) {
        for (HtmlModificationListener listener : htmlModificationListeners) {
            listener.fireConditionModification(condition, tagNode);
        }
    }

    @Override
    public void fireHtmlError(boolean certainty, TagNode startTagToken, ErrorType type) {
        for (HtmlModificationListener listener : htmlModificationListeners) {
            listener.fireHtmlError(certainty, startTagToken, type);
        }
        
    }

    @Override
    public void fireUglyHtml(boolean certainty, TagNode startTagToken, ErrorType errorType) {
        for (HtmlModificationListener listener : htmlModificationListeners) {
            listener.fireUglyHtml(certainty, startTagToken, errorType);
        }
    }

    @Override
    public void fireUserDefinedModification(boolean certainty, TagNode tagNode, ErrorType errorType) {
        for (HtmlModificationListener listener : htmlModificationListeners) {
            listener.fireUserDefinedModification(certainty, tagNode, errorType);
        }
    }
}