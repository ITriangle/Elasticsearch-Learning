package com.seentech.web;

/**
 * Created by seentech on 2017/2/10.
 */
public class QueryParam {
    /**
     * query : {"term":{"id":"3815027"}}
     */

    private QueryBean query;


    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public static class QueryBean {
        /**
         * term : {"id":"3815027"}
         */

        private TermBean term;

        public TermBean getTerm() {
            return term;
        }

        public void setTerm(TermBean term) {
            this.term = term;
        }

        public static class TermBean {
            /**
             * id : 3815027
             */

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
