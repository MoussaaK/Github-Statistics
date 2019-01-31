public void addRepositories() throws ClientProtocolException, IOException {
    	CloseableHttpClient client= null;
        CloseableHttpResponse response= null;

        client= HttpClients.createDefault();
        HttpPost httpPost= new HttpPost("https://api.github.com/graphql");

        httpPost.addHeader("Authorization","Bearer 4fc69cbe8a3f12c7a41c217235210362527b6fc1");
        httpPost.addHeader("Accept","application/json");
        
        JSONObject GraphQLQuery = new JSONObject();     
        GraphQLQuery.put("query", "query {viewer {name repositories(last: 5) { nodes { name }}}}");
        
        StringEntity entity = new StringEntity(GraphQLQuery.toString());
        httpPost.setEntity(entity);
        response = client.execute(httpPost);
    }