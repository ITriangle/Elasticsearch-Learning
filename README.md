# 1. 通过路径,指明参数 
curl -XGET 'http://192.168.4.84:9200/mac_2020_01_01_01/type/?pretty'

# 2. 通过路径和简单参数,指明参数
curl -XGET 'http://192.168.4.84:9200/mac_2020_01_01_01/type/_search?pretty&q=id:3815027'

# 3. 通过 body 指明参数
curl -XGET 'http://192.168.4.84:9200/mac_2020_01_01_01/type/_search?pretty' -d '{"query": {"term": {"id": "3815027"}}}'


# 1. 对应上面的 1
curl -XGET 'http://localhost:8080/mac_2020_01_01_01/type/3815027/_search'

# 2. 对应上面的 2
curl -XGET 'http://localhost:8080/mac_2020_01_01_01/type/_get?q=id:3815027'

# 3. 对应上面的 3
curl -XGET 'http://localhost:8080/mac_2020_01_01_01/type/_search' -d '{"query": {"term": {"id": "3815027"}}}'



#导入数据
curl -XPOST 'localhost:9200/mac_2020_01_01_01/type/_bulk?pretty' --data-binary  @mac_2020_01_01_01_type.json


